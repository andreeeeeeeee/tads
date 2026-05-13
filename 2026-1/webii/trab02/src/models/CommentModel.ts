import db from '../config/db';

export type Comment = {
  id: number;
  productId: number;
  userId: number;
  content: string;
  imageUrl?: string;
  createdAt: Date;
};

export function create(comment: Omit<Comment, 'id' | 'createdAt'>): Comment {
  const stmt = db.prepare(`
    INSERT INTO comments (product_id, user_id, content, image_url)
    VALUES (?, ?, ?, ?)
  `);
  const info = stmt.run(
    comment.productId,
    comment.userId,
    comment.content,
    comment.imageUrl || null
  );
  const row = db.prepare('SELECT * FROM comments WHERE id = ?').get(info.lastInsertRowid);
  return mapRowToComment(row);
}

export function findByProduct(productId: number): Comment[] {
  const rows = db.prepare('SELECT * FROM comments WHERE product_id = ? ORDER BY created_at DESC').all(productId);
  return rows.map(mapRowToComment);
}

export function findById(id: number): Comment | undefined {
  const row = db.prepare('SELECT * FROM comments WHERE id = ?').get(id);
  if (!row) return undefined;
  return mapRowToComment(row);
}

export function update(id: number, content: string, imageUrl?: string): void {
  db.prepare('UPDATE comments SET content = ?, image_url = ? WHERE id = ?').run(content, imageUrl || null, id);
}

export function remove(id: number): void {
  db.prepare('DELETE FROM comments WHERE id = ?').run(id);
}

function mapRowToComment(row: any): Comment {
  return {
    id: row.id,
    productId: row.product_id,
    userId: row.user_id,
    content: row.content,
    imageUrl: row.image_url || undefined,
    createdAt: new Date(row.created_at),
  };
}
