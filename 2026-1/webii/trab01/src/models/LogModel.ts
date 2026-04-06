import db from '../config/db';

export interface LogEntry {
  id?: number;
  timestamp?: string;
  user_id?: number | null;
  method: string;
  endpoint: string;
  summary: string;
}

export function insertLog(log: LogEntry) {
  db.prepare(
    `INSERT INTO logs (user_id, method, endpoint, summary) VALUES (?, ?, ?, ?)`
  ).run(log.user_id ?? null, log.method, log.endpoint, log.summary);
}

export function getAllLogs(): LogEntry[] {
  return db.prepare('SELECT * FROM logs ORDER BY timestamp DESC').all() as LogEntry[];
}
