import { Request, Response, Router } from 'express';
import { requireAuth, requireRole } from '../middleware/requireAuth';
import * as ProductImageModel from '../models/ProductImageModel';
import * as ProductModel from '../models/ProductModel';

function dashboard(req: Request, res: Response) {
  const produtos = ProductModel.findByUser(req.session.user!.id).map((p) => ({
    ...p,
    imagens: ProductImageModel.findByProduct(p.id),
  }));
  res.render('seller-dashboard', {
    produtos,
    error: typeof req.query.error === 'string' ? req.query.error : null,
    success: typeof req.query.success === 'string' ? req.query.success : null,
  });
}

const router = Router();

router.get('/seller-dashboard', requireAuth, requireRole('vendedor'), dashboard);

export default router;
