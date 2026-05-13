import { Request, Response, Router } from 'express';
import { requireAuth, requireRole } from '../middleware/requireAuth';
import * as UserModel from '../models/UserModel';
import { zodIssues } from '../validation/helpers';
import { buyerProfileSchema } from '../validation/schemas';

function profile(req: Request, res: Response) {
  const user = UserModel.findById(req.session.user!.id);
  res.render('buyer-profile', { user });
}

function showEdit(req: Request, res: Response) {
  const user = UserModel.findById(req.session.user!.id);
  res.render('buyer-edit', { user, errors: undefined });
}

function update(req: Request, res: Response) {
  const userId = req.session.user!.id;
  const user = UserModel.findById(userId);
  if (!user) return res.redirect('/login');

  const parsed = buyerProfileSchema.safeParse(req.body);
  if (!parsed.success) {
    return res.render('buyer-edit', { user, errors: zodIssues(parsed.error) });
  }

  const result = UserModel.updateBuyerProfile(userId, parsed.data);
  if (result.error) {
    return res.render('buyer-edit', { user, errors: [result.error] });
  }
  res.redirect('/comprador/perfil');
}

const router = Router();
const onlyBuyer = requireRole('comprador');

router.get('/comprador/perfil', requireAuth, onlyBuyer, profile);
router.get('/comprador/editar', requireAuth, onlyBuyer, showEdit);
router.post('/comprador/editar', requireAuth, onlyBuyer, update);

router.get('/cart', requireAuth, onlyBuyer, (_req, res) => {
  res.render('cart');
});
router.get('/checkout', requireAuth, onlyBuyer, (_req, res) => {
  res.render('checkout');
});
router.get('/orders', requireAuth, onlyBuyer, (_req, res) => {
  res.render('orders');
});

export default router;
