import { NextFunction, Request, Response, Router } from 'express';
import { productImageUpload } from '../config/upload';
import * as ProductModel from '../models/ProductModel';
import { storeProductImage } from '../services/product-image-storage';

const router = Router();

interface MulterRequest extends Request {
  file?: Express.Multer.File;
}

function requireSeller(req: Request, res: Response, next: NextFunction) {
  const role = req.session.user?.role;
  if (!req.session.user || (role !== 'vendedor' && role !== 'admin')) {
    if (req.accepts('html')) {
      return res.redirect('/login?error=Entre como vendedor para publicar produtos.');
    }
    return res.status(403).json({ message: 'Acesso restrito a vendedores.' });
  }
  next();
}

router.get('/dashboard', requireSeller, async (req: Request, res: Response) => {
  const produtos = ProductModel.findByUser(req.session.user?.id || 0);
  res.render('seller-dashboard', { produtos });
});

router.post('/cadastrar', requireSeller, productImageUpload.single('image'), async (req: MulterRequest, res: Response) => {
  const { nome, descricao, preco, categoria, estoque } = req.body;
  let imageUrl = '';
  if (req.file) {
    const stored = await storeProductImage(req.file);
    imageUrl = stored.imageUrl;
  }
  ProductModel.create({
    userId: req.session.user?.id || 0,
    name: nome,
    description: descricao,
    category: categoria,
    price: Number(preco),
    stock: Number(estoque),
    imageUrl,
    imageStorage: 'local',
  });
  res.redirect('/produtos/dashboard');
});

export default router;
