import fs from 'node:fs';
import type { ZodError } from 'zod';

export function zodIssues(err: ZodError): string[] {
  return err.issues.map((e) => {
    const path = e.path.length ? `${e.path.join('.')}: ` : '';
    return `${path}${e.message}`;
  });
}

export function unlinkMulterFiles(files: Express.Multer.File[] | undefined): void {
  files?.forEach(unlinkMulterFile);
}

export function unlinkMulterFile(file: Express.Multer.File | undefined): void {
  if (!file?.path) return;
  try {
    fs.unlinkSync(file.path);
  } catch {
    // ignore
  }
}
