import fs from 'node:fs';
import path from 'node:path';
import db from '../config/db';

export type ProductImage = {
  id: number;
  productId: number;
  imageUrl: string;
  isMain: boolean;
  createdAt: Date;
};

export const MAX_IMAGES_PER_PRODUCT = 8;

export function add(productId: number, imageUrl: string, isMain = false): ProductImage {
  const stmt = db.prepare(`INSERT INTO product_images (product_id, image_url, is_main) VALUES (?, ?, ?)`);
  const info = stmt.run(productId, imageUrl, isMain ? 1 : 0);
  const row = db.prepare('SELECT * FROM product_images WHERE id = ?').get(info.lastInsertRowid);
  return mapRowToImage(row);
}

export function findByProduct(productId: number): ProductImage[] {
  const rows = db
    .prepare('SELECT * FROM product_images WHERE product_id = ? ORDER BY is_main DESC, created_at ASC')
    .all(productId);
  return rows.map(mapRowToImage);
}

export function findById(id: number): ProductImage | undefined {
  const row = db.prepare('SELECT * FROM product_images WHERE id = ?').get(id);
  if (!row) return undefined;
  return mapRowToImage(row);
}

export function findMain(productId: number): ProductImage | undefined {
  const row = db
    .prepare('SELECT * FROM product_images WHERE product_id = ? AND is_main = 1 LIMIT 1')
    .get(productId);
  if (!row) return undefined;
  return mapRowToImage(row);
}

export function findFirst(productId: number): ProductImage | undefined {
  const row = db
    .prepare('SELECT * FROM product_images WHERE product_id = ? ORDER BY is_main DESC, created_at ASC LIMIT 1')
    .get(productId);
  if (!row) return undefined;
  return mapRowToImage(row);
}

export function countByProduct(productId: number): number {
  const row = db
    .prepare('SELECT COUNT(*) as c FROM product_images WHERE product_id = ?')
    .get(productId) as { c: number } | undefined;
  return row?.c ?? 0;
}

export function setMain(productId: number, imageId: number): void {
  const tx = db.transaction(() => {
    db.prepare('UPDATE product_images SET is_main = 0 WHERE product_id = ?').run(productId);
    db.prepare('UPDATE product_images SET is_main = 1 WHERE id = ? AND product_id = ?').run(imageId, productId);
  });
  tx();
}

export function remove(id: number): ProductImage | undefined {
  const image = findById(id);
  if (!image) return undefined;

  db.prepare('DELETE FROM product_images WHERE id = ?').run(id);

  if (image.isMain) {
    const next = findFirst(image.productId);
    if (next) {
      db.prepare('UPDATE product_images SET is_main = 1 WHERE id = ?').run(next.id);
    }
  }

  removeFileIfLocal(image.imageUrl);
  return image;
}

function removeFileIfLocal(imageUrl: string): void {
  if (!imageUrl || !imageUrl.startsWith('/uploads/')) return;
  const appRoot = path.resolve(__dirname, '..', '..');
  const fsPath = path.join(appRoot, imageUrl.replace(/^\//, ''));
  fs.promises.unlink(fsPath).catch(() => {
    // Silently ignore — arquivo pode já ter sido removido manualmente.
  });
}

function mapRowToImage(row: any): ProductImage {
  return {
    id: row.id,
    productId: row.product_id,
    imageUrl: row.image_url,
    isMain: !!row.is_main,
    createdAt: new Date(row.created_at),
  };
}
