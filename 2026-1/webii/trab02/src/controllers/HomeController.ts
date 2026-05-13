import { Request, Response, Router } from 'express';
import { requireAuth, requireRole } from '../middleware/requireAuth';
import * as ProductModel from '../models/ProductModel';

const router = Router();
const onlyBuyer = requireRole('comprador');

function index(_req: Request, res: Response): void {
  const produtos = ProductModel.findAll();
  res.render('index', { produtos });
}

router.get('/', index);
router.get('/index', index);
router.get('/index.html', index);

router.get('/categories', (req, res) => {
  const rawCategory = typeof req.query.categoria === 'string' ? req.query.categoria.trim() : '';
  const categorias = ProductModel.listCategories();

  const matched = rawCategory
    ? categorias.find((c) => c.name.toLowerCase() === rawCategory.toLowerCase()) ?? null
    : null;

  const produtos = matched
    ? ProductModel.findByCategory(matched.name)
    : ProductModel.findAll();

  res.render('categories', {
    categorias,
    produtos,
    selectedCategory: matched?.name ?? null,
  });
});

router.get('/product-details', (req, res) => {
  res.render('product-details', {
    produto: null,
    vendedor: null,
    comentarios: [],
    imagens: [],
    mainImage: null,
    commentError: null,
    currentUser: req.session.user ?? null,
  });
});

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
