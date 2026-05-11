import multer from 'multer';
import fs from 'node:fs';
import path from 'node:path';

export type UploadDriver = 'local' | 's3';

const allowedMimeTypes = ['image/jpeg', 'image/png', 'image/webp', 'image/gif'];
const maxFileSizeInBytes = 5 * 1024 * 1024;
const uploadDriver: UploadDriver = process.env.UPLOAD_DRIVER === 's3' ? 's3' : 'local';
const appRootDir = path.resolve(__dirname, '..', '..');
const uploadRootDir = path.join(appRootDir, 'uploads');
const productUploadDir = path.join(uploadRootDir, 'products');

function sanitizeBaseName(fileName: string) {
  return path
    .parse(fileName)
    .name
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .replace(/[^a-zA-Z0-9_-]/g, '-')
    .replace(/-+/g, '-')
    .replace(/^-|-$/g, '')
    .toLowerCase();
}

function buildFileName(file: Express.Multer.File) {
  const extension = path.extname(file.originalname).toLowerCase() || '.bin';
  const baseName = sanitizeBaseName(file.originalname) || 'produto';
  return `${Date.now()}-${baseName}${extension}`;
}

const localStorage = multer.diskStorage({
  destination: (_req, _file, callback) => {
    fs.mkdirSync(productUploadDir, { recursive: true });
    callback(null, productUploadDir);
  },
  filename: (_req, file, callback) => {
    callback(null, buildFileName(file));
  },
});

export const productImageUpload = multer({
  storage: localStorage,
  fileFilter: (_req, file, callback) => {
    if (!allowedMimeTypes.includes(file.mimetype)) {
      return callback(new Error('Tipo de arquivo não permitido.'));
    }
    callback(null, true);
  },
  limits: { fileSize: maxFileSizeInBytes },
});

export function getLocalProductImageUrl(fileName: string) {
  return `/uploads/products/${fileName}`;
}

export function getUploadDriver() {
  return uploadDriver;
}
