import { Request, Response, Router } from 'express';
import { requireAuth, requireRole } from '../middleware/requireAuth';
import * as UserModel from '../models/UserModel';
import { adminUserIdParamSchema } from '../validation/schemas';

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

function showDashboard(req: Request, res: Response): void {
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

function setUserActive(req: Request, res: Response): void {
  const adminId = req.session.user!.id;
  const idParsed = adminUserIdParamSchema.safeParse(req.params);
  if (!idParsed.success) {
    res.redirect('/admin-dashboard?error=invalid');
    return;
  }
  const id = idParsed.data.id;

  const wantActive = req.body.active === '1' || req.body.active === 1 || req.body.active === true;

  if (id === adminId && !wantActive) {
    res.redirect('/admin-dashboard?error=self');
    return;
  }

  const target = UserModel.findById(id);
  if (!target) {
    res.redirect('/admin-dashboard?error=notfound');
    return;
  }

  if (!wantActive && target.role === 'admin' && target.active && UserModel.countActiveAdmins() <= 1) {
    res.redirect('/admin-dashboard?error=lastadmin');
    return;
  }

  UserModel.setUserActive(id, wantActive);
  res.redirect('/admin-dashboard?ok=1');
}

const router = Router();
const onlyAdmin = requireRole('admin');

router.get('/admin-dashboard', requireAuth, onlyAdmin, showDashboard);
router.post('/admin/users/:id/active', requireAuth, onlyAdmin, setUserActive);

export default router;
