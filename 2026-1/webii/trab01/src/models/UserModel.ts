import bcrypt from 'bcryptjs';
import crypto from 'crypto';
import db from '../config/db';
import { verificationTtlMs } from '../services/emailService';

export type UserRole = 'admin' | 'comprador' | 'vendedor';

export interface UserRow {
  id: number;
  name: string;
  email: string;
  password_hash: string;
  role: UserRole;
  email_verified: boolean;
  active: number;
  created_at: string;
  email_verify_code: string | null;
  email_verify_expires_at: string | null;
}

function rowToUser(raw: Record<string, unknown>): UserRow {
  return {
    id: raw.id as number,
    name: raw.name as string,
    email: raw.email as string,
    password_hash: raw.password_hash as string,
    role: raw.role as UserRole,
    email_verified: Boolean(raw.email_verified),
    active: Number(raw.active) ? 1 : 0,
    created_at: raw.created_at as string,
    email_verify_code: raw.email_verify_code != null ? String(raw.email_verify_code) : null,
    email_verify_expires_at: raw.email_verify_expires_at != null ? String(raw.email_verify_expires_at) : null,
  };
}

export function findByEmail(email: string): UserRow | undefined {
  const normalized = email.trim().toLowerCase();
  const raw = db.prepare('SELECT * FROM users WHERE email = ?').get(normalized) as
    | Record<string, unknown>
    | undefined;
  return raw ? rowToUser(raw) : undefined;
}

export function findById(id: number): UserRow | undefined {
  const raw = db.prepare('SELECT * FROM users WHERE id = ?').get(id) as Record<string, unknown> | undefined;
  return raw ? rowToUser(raw) : undefined;
}

export type AdminUserListItem = {
  id: number;
  name: string;
  email: string;
  role: UserRole;
  email_verified: boolean;
  active: boolean;
  created_at: string;
};

export function listUsersForAdmin(): AdminUserListItem[] {
  const rows = db
    .prepare(
      `SELECT id, name, email, role, email_verified, active, created_at
       FROM users ORDER BY name COLLATE NOCASE`,
    )
    .all() as Record<string, unknown>[];
  return rows.map((r) => ({
    id: r.id as number,
    name: r.name as string,
    email: r.email as string,
    role: r.role as UserRole,
    email_verified: Boolean(r.email_verified),
    active: Boolean(r.active),
    created_at: r.created_at as string,
  }));
}

export function setUserActive(id: number, active: boolean): void {
  db.prepare('UPDATE users SET active = ? WHERE id = ?').run(active ? 1 : 0, id);
}

export function countActiveAdmins(): number {
  const row = db.prepare(
    `SELECT COUNT(*) as c FROM users WHERE role = 'admin' AND active = 1`,
  ).get() as { c: number };
  return row.c;
}

export function createUser(params: {
  name: string;
  email: string;
  plainPassword: string;
  role: 'comprador' | 'vendedor';
}): number {
  const email = params.email.trim().toLowerCase();
  const passwordHash = bcrypt.hashSync(params.plainPassword, 10);
  const result = db
    .prepare(
      `INSERT INTO users (name, email, password_hash, role, email_verified, active)
       VALUES (@name, @email, @password_hash, @role, 0, 1)`,
    )
    .run({
      name: params.name.trim(),
      email,
      password_hash: passwordHash,
      role: params.role,
    });
  return Number(result.lastInsertRowid);
}

export function issueNewVerificationCodeForUserId(userId: number): string {
  const code = crypto.randomInt(100000, 1000000).toString();
  const expiresAt = new Date(Date.now() + verificationTtlMs()).toISOString();
  db.prepare(
    `UPDATE users SET email_verify_code = ?, email_verify_expires_at = ? WHERE id = ?`,
  ).run(code, expiresAt, userId);
  return code;
}

export type VerifyCodeResult =
  | { ok: true }
  | {
    ok: false;
    reason: 'not_found' | 'already_verified' | 'invalid_code' | 'expired' | 'no_code';
  };

export function verifyEmailWithCode(email: string, inputCode: string): VerifyCodeResult {
  const user = findByEmail(email);
  if (!user)
    return { ok: false, reason: 'not_found' };
  if (user.email_verified)
    return { ok: false, reason: 'already_verified' };
  const stored = user.email_verify_code;
  const exp = user.email_verify_expires_at;
  if (!stored || !exp)
    return { ok: false, reason: 'no_code' };
  if (new Date(exp).getTime() < Date.now())
    return { ok: false, reason: 'expired' };

  const a = inputCode.trim().replace(/\s+/g, '');
  const b = stored.trim();
  if (a.length !== b.length)
    return { ok: false, reason: 'invalid_code' };
  try {
    if (!crypto.timingSafeEqual(Buffer.from(a, 'utf8'), Buffer.from(b, 'utf8')))
      return { ok: false, reason: 'invalid_code' };
  } catch {
    return { ok: false, reason: 'invalid_code' };
  }
  db.prepare(
    `UPDATE users SET email_verified = 1, email_verify_code = NULL, email_verify_expires_at = NULL WHERE id = ?`,
  ).run(user.id);
  return { ok: true };
}

export type AuthFailure = 'not_found' | 'wrong_password' | 'inactive' | 'unverified';

export function authenticate(
  email: string,
  plainPassword: string,
): { ok: true; user: UserRow } | { ok: false; reason: AuthFailure } {
  const user = findByEmail(email);
  if (!user)
    return { ok: false, reason: 'not_found' };
  if (!user.active)
    return { ok: false, reason: 'inactive' };
  const allowUnverified = process.env.ALLOW_UNVERIFIED_LOGIN === '1';
  if (!user.email_verified && !allowUnverified)
    return { ok: false, reason: 'unverified' };
  if (!bcrypt.compareSync(plainPassword, user.password_hash))
    return { ok: false, reason: 'wrong_password' };

  return { ok: true, user };
}
