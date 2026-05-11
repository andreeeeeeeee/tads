import bcrypt from 'bcryptjs';
import Database from 'better-sqlite3';

const db = new Database('banco.db', {
  verbose: console.log,
  timeout: 10000
});

db.exec(`
  CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    telefone TEXT NOT NULL DEFAULT '',
    endereco TEXT NOT NULL DEFAULT '',
    cidade TEXT NOT NULL DEFAULT '',
    estado TEXT NOT NULL DEFAULT '',
    cep TEXT NOT NULL DEFAULT '',
    pagamento TEXT NOT NULL DEFAULT '',
    role TEXT NOT NULL,
    email_verified BOOLEAN NOT NULL DEFAULT false,
    active BOOLEAN NOT NULL DEFAULT true,
    email_verify_code TEXT,
    email_verify_expires_at TEXT,
    created_at TEXT NOT NULL DEFAULT (datetime('now'))
  );
`);

db.exec(`
  CREATE TABLE IF NOT EXISTS logs (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    user_id INTEGER,
    method TEXT NOT NULL,
    endpoint TEXT NOT NULL,
    summary TEXT NOT NULL
  );
`);

db.exec(`
  CREATE TABLE IF NOT EXISTS products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    category TEXT NOT NULL,
    price REAL NOT NULL,
    stock INTEGER NOT NULL,
    image_url TEXT,
    image_storage TEXT,
    created_at TEXT NOT NULL DEFAULT (datetime('now')),
    FOREIGN KEY(user_id) REFERENCES users(id)
  );
`);

// db.exec(`
//   INSERT INTO users (id, name, email, password_hash, role, email_verified) VALUES
//     (1, 'Admin User', 'admin@teste.com', '${bcrypt.hashSync('Admin@123', 10)}', 'admin', true),
//     (2, 'Comprador User', 'comprador@teste.com', '${bcrypt.hashSync('Comprador@123', 10)}', 'comprador', true),
//     (3, 'Vendedor User', 'vendedor@teste.com', '${bcrypt.hashSync('Vendedor@123', 10)}', 'vendedor', true);
// `);

export default db;