import { Request, Response, Router } from 'express';
import * as ProductModel from '../models/ProductModel';

const router = Router();

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

export default router;
