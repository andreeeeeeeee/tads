import db from '../config/db';

export type Product = {
  id: number;
  userId: number;
  name: string;
  description: string;
  category: string;
  price: number;
  stock: number;
  imageUrl: string;
  imageStorage: string;
  createdAt: Date;
};

export function create(product: Omit<Product, 'id' | 'createdAt'>): Product {
  const stmt = db.prepare(`
    INSERT INTO products (user_id, name, description, category, price, stock, image_url, image_storage)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
  `);
  const info = stmt.run(
    product.userId,
    product.name,
    product.description,
    product.category,
    product.price,
    product.stock,
    product.imageUrl,
    product.imageStorage
  );
  const row = db.prepare('SELECT * FROM products WHERE id = ?').get(info.lastInsertRowid);
  return mapRowToProduct(row);
}

export function findByUser(userId: number): Product[] {
  const rows = db.prepare('SELECT * FROM products WHERE user_id = ? ORDER BY created_at DESC').all(userId);
  return rows.map(mapRowToProduct);
}

export function findAll(): Product[] {
  const rows = db.prepare('SELECT * FROM products ORDER BY created_at DESC').all();
  return rows.map(mapRowToProduct);
}

export function findByCategory(category: string): Product[] {
  const rows = db
    .prepare(
      `SELECT * FROM products
       WHERE LOWER(TRIM(category)) = LOWER(TRIM(?))
       ORDER BY created_at DESC`,
    )
    .all(category);
  return rows.map(mapRowToProduct);
}

export type CategorySummary = { name: string; count: number };

export function listCategories(): CategorySummary[] {
  const rows = db
    .prepare(
      `SELECT TRIM(category) AS name, COUNT(*) AS count
       FROM products
       WHERE category IS NOT NULL AND TRIM(category) <> ''
       GROUP BY TRIM(category)
       ORDER BY count DESC, name COLLATE NOCASE ASC`,
    )
    .all() as { name: string; count: number }[];
  return rows;
}

export function findById(id: number): Product | undefined {
  const row = db.prepare('SELECT * FROM products WHERE id = ?').get(id);
  if (!row) return undefined;
  return mapRowToProduct(row);
}

export function updateMainImageUrl(productId: number, imageUrl: string): void {
  db.prepare('UPDATE products SET image_url = ? WHERE id = ?').run(imageUrl, productId);
}

function mapRowToProduct(row: any): Product {
  return {
    id: row.id,
    userId: row.user_id,
    name: row.name,
    description: row.description,
    category: row.category,
    price: row.price,
    stock: row.stock,
    imageUrl: row.image_url,
    imageStorage: row.image_storage,
    createdAt: new Date(row.created_at),
  };
}
