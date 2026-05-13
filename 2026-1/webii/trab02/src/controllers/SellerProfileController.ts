import { Request, Response, Router } from 'express';
import { requireAuth, requireRole } from '../middleware/requireAuth';
import * as ProductImageModel from '../models/ProductImageModel';
import * as ProductModel from '../models/ProductModel';
import * as UserModel from '../models/UserModel';
import { zodIssues } from '../validation/helpers';
import { sellerProfileSchema } from '../validation/schemas';

function profile(req: Request, res: Response) {
  const user = UserModel.findById(req.session.user!.id);
  res.render('seller-profile', { user });
}

function showEdit(req: Request, res: Response) {
  const user = UserModel.findById(req.session.user!.id);
  res.render('seller-edit', { user, errors: undefined });
}

function update(req: Request, res: Response) {
  const userId = req.session.user!.id;
  const user = UserModel.findById(userId);
  if (!user) return res.redirect('/login');

  const parsed = sellerProfileSchema.safeParse(req.body);
  if (!parsed.success) {
    return res.render('seller-edit', { user, errors: zodIssues(parsed.error) });
  }

  const result = UserModel.updateSellerProfile(userId, parsed.data);
  if (result.error) {
    return res.render('seller-edit', { user, errors: [result.error] });
  }
  res.redirect('/vendedor/perfil');
}

function publicProfile(req: Request, res: Response) {
  const id = Number(req.params.id);
  if (!Number.isFinite(id) || id <= 0) {
    return res.status(404).render('seller-public-profile', { vendedor: null, produtos: [] });
  }

  const vendedor = UserModel.findById(id);
  if (!vendedor || vendedor.role !== 'vendedor' || !vendedor.active) {
    return res.status(404).render('seller-public-profile', { vendedor: null, produtos: [] });
  }

  const produtos = ProductModel.findByUser(vendedor.id).map((p) => ({
    ...p,
    imagens: ProductImageModel.findByProduct(p.id),
  }));

  res.render('seller-public-profile', { vendedor, produtos });
}

const router = Router();
const onlySeller = requireRole('vendedor');

router.get('/vendedor/perfil', requireAuth, onlySeller, profile);
router.get('/vendedor/editar', requireAuth, onlySeller, showEdit);
router.post('/vendedor/editar', requireAuth, onlySeller, update);
router.get('/vendedor/:id(\\d+)', publicProfile);

export default router;
