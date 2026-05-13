import db from '../config/db';

export type CommentLike = {
  id: number;
  commentId: number;
  userId: number;
  createdAt: Date;
};

export function like(commentId: number, userId: number): boolean {
  try {
    db.prepare('INSERT INTO comment_likes (comment_id, user_id) VALUES (?, ?)').run(commentId, userId);
    return true;
  } catch (e) {
    return false; // Já curtido
  }
}

export function unlike(commentId: number, userId: number): void {
  db.prepare('DELETE FROM comment_likes WHERE comment_id = ? AND user_id = ?').run(commentId, userId);
}

export function count(commentId: number): number {
  const row = db.prepare('SELECT COUNT(*) as total FROM comment_likes WHERE comment_id = ?').get(commentId) as { total: number };
  return row.total;
}

export function isLikedByUser(commentId: number, userId: number): boolean {
  const row = db.prepare('SELECT 1 FROM comment_likes WHERE comment_id = ? AND user_id = ?').get(commentId, userId);
  return !!row;
}
