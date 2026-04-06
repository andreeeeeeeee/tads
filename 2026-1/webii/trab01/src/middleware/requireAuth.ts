import { NextFunction, Request, Response } from 'express';
import type { UserRole } from '../models/UserModel';
import * as UserModel from '../models/UserModel';

const roleLabels: Record<UserRole, string> = {
  admin: 'administrador',
  comprador: 'comprador',
  vendedor: 'vendedor',
};

export function sendForbidden(res: Response, message: string): void {
  res.status(403).type('html').send(`<!DOCTYPE html>
<html lang="pt-BR"><head><meta charset="utf-8"/><title>Acesso negado</title></head>
<body style="font-family:system-ui,sans-serif;padding:2rem;max-width:36rem;line-height:1.5">
<p>${message}</p>
<p><a href="/index.html">Voltar ao início</a></p>
</body></html>`);
}

export function requireAuth(req: Request, res: Response, next: NextFunction): void {
  if (!req.session.user) {
    const dest = encodeURIComponent(req.originalUrl.split('?')[0] || '/');
    res.redirect(`/login.html?next=${dest}`);
    return;
  }

  const fresh = UserModel.findById(req.session.user.id);
  if (!fresh || !fresh.active) {
    req.session.destroy(() => {
      res.redirect('/login.html?deactivated=1');
    });
    return;
  }

  next();
}

export function requireRole(...allowed: UserRole[]) {
  return function roleGuard(req: Request, res: Response, next: NextFunction): void {
    const role = req.session.user?.role;
    if (!role || !allowed.includes(role as UserRole)) {
      const need = allowed.map((r) => roleLabels[r]).join(' ou ');
      sendForbidden(
        res,
        `Acesso negado. Esta área é restrita a perfil de <strong>${need}</strong>.`,
      );
      return;
    }
    next();
  };
}
