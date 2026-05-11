import { Request, Response } from 'express';
import * as UserModel from '../models/UserModel';

function flashError(code: string | undefined): string | null {
  switch (code) {
    case 'self':
      return 'Você não pode desativar a própria conta enquanto estiver logado.';
    case 'lastadmin':
      return 'Não é possível desativar o último administrador ativo da plataforma.';
    case 'notfound':
      return 'Usuário não encontrado.';
    case 'invalid':
      return 'Requisição inválida.';
    default:
      return null;
  }
}

export function showDashboard(req: Request, res: Response): void {
  const users = UserModel.listUsersForAdmin();
  const activeCount = users.filter((u) => u.active).length;
  res.render('admin-dashboard', {
    users,
    stats: {
      total: users.length,
      active: activeCount,
      inactive: users.length - activeCount,
    },
    adminSession: req.session.user!,
    flashOk: req.query.ok === '1',
    flashError: flashError(typeof req.query.error === 'string' ? req.query.error : undefined),
  });
}

export function setUserActive(req: Request, res: Response): void {
  const adminId = req.session.user!.id;
  const id = parseInt(req.params.id, 10);
  if (!Number.isFinite(id) || id < 1) {
    res.redirect('/admin-dashboard.html?error=invalid');
    return;
  }

  const wantActive = req.body.active === '1' || req.body.active === 1 || req.body.active === true;

  if (id === adminId && !wantActive) {
    res.redirect('/admin-dashboard.html?error=self');
    return;
  }

  const target = UserModel.findById(id);
  if (!target) {
    res.redirect('/admin-dashboard.html?error=notfound');
    return;
  }

  if (!wantActive && target.role === 'admin' && target.active && UserModel.countActiveAdmins() <= 1) {
    res.redirect('/admin-dashboard.html?error=lastadmin');
    return;
  }

  UserModel.setUserActive(id, wantActive);
  res.redirect('/admin-dashboard.html?ok=1');
}
