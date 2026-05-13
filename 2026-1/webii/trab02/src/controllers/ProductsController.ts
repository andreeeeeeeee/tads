import { NextFunction, Request, Response, Router } from 'express';
import multer from 'multer';
import { z } from 'zod';
import { productImageUpload } from '../config/upload';
import * as CommentLikeModel from '../models/CommentLikeModel';
import * as CommentModel from '../models/CommentModel';
import * as ProductImageModel from '../models/ProductImageModel';
import * as ProductModel from '../models/ProductModel';
import * as UserModel from '../models/UserModel';
import { storeProductImage } from '../services/product-image-storage';
import { unlinkMulterFiles, zodIssues } from '../validation/helpers';
import { positiveInteger, productCreateBodySchema } from '../validation/schemas';

const router = Router();

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

function requireProductOwner(req: Request, res: Response, next: NextFunction) {
  const idParsed = positiveInteger.safeParse(req.params.id);
  if (!idParsed.success) {
    return res.status(404).send('Produto não encontrado.');
  }
  const produto = ProductModel.findById(idParsed.data);
  if (!produto) {
    return res.status(404).send('Produto não encontrado.');
  }

  const user = req.session.user;
  if (!user || (produto.userId !== user.id && user.role !== 'admin')) {
    return res.status(403).send('Você não tem permissão para alterar este produto.');
  }

  (req as any).produto = produto;
  next();
}

function handleUploadErrors(uploader: multer.Multer, field: string, max: number) {
  const handler = uploader.array(field, max);
  return (req: Request, res: Response, next: NextFunction) => {
    handler(req, res, (err: any) => {
      if (!err) return next();
      const message =
        err instanceof multer.MulterError
          ? err.code === 'LIMIT_FILE_SIZE'
            ? 'Cada imagem deve ter no máximo 5MB.'
            : err.code === 'LIMIT_FILE_COUNT' || err.code === 'LIMIT_UNEXPECTED_FILE'
              ? `Envie no máximo ${max} imagens por vez.`
              : 'Falha ao enviar imagens.'
          : err.message || 'Falha ao enviar imagens.';
      const back = req.get('referer') || '/produtos/dashboard';
      const sep = back.includes('?') ? '&' : '?';
      return res.redirect(`${back}${sep}error=${encodeURIComponent(message)}`);
    });
  };
}

router.get('/dashboard', requireSeller, async (req: Request, res: Response) => {
  const produtos = ProductModel.findByUser(req.session.user!.id).map((p) => ({
    ...p,
    imagens: ProductImageModel.findByProduct(p.id),
  }));
  res.render('seller-dashboard', {
    produtos,
    error: typeof req.query.error === 'string' ? req.query.error : null,
    success: typeof req.query.success === 'string' ? req.query.success : null,
  });
});

router.post(
  '/cadastrar',
  requireSeller,
  handleUploadErrors(productImageUpload, 'images', ProductImageModel.MAX_IMAGES_PER_PRODUCT),
  async (req: Request, res: Response) => {
    const files = (req.files as Express.Multer.File[] | undefined) || [];

    if (files.length === 0) {
      return res.redirect(
        '/produtos/dashboard?error=' + encodeURIComponent('Envie pelo menos uma imagem do produto.'),
      );
    }

    const parsed = productCreateBodySchema.safeParse(req.body);
    if (!parsed.success) {
      unlinkMulterFiles(files);
      return res.redirect(
        '/produtos/dashboard?error=' + encodeURIComponent(zodIssues(parsed.error).join(' • ')),
      );
    }

    const { nome, descricao, categoria, preco, estoque } = parsed.data;
    const mainIndexParsed = positiveInteger.safeParse(req.body.main_index ?? 0);
    const mainIndexRaw = mainIndexParsed.success ? mainIndexParsed.data : 0;
    const mainIndex = mainIndexRaw < files.length ? mainIndexRaw : 0;

    const stored = await Promise.all(files.map((file) => storeProductImage(file)));
    const mainStored = stored[mainIndex];

    const produto = ProductModel.create({
      userId: req.session.user!.id,
      name: nome,
      description: descricao,
      category: categoria,
      price: preco,
      stock: estoque,
      imageUrl: mainStored.imageUrl,
      imageStorage: mainStored.imageStorage,
    });

    stored.forEach((s, idx) => {
      ProductImageModel.add(produto.id, s.imageUrl, idx === mainIndex);
    });

    res.redirect(
      '/produtos/dashboard?success=' + encodeURIComponent(`Produto cadastrado com ${files.length} foto(s).`),
    );
  },
);

router.post(
  '/:id/imagens',
  requireSeller,
  requireProductOwner,
  handleUploadErrors(productImageUpload, 'images', ProductImageModel.MAX_IMAGES_PER_PRODUCT),
  async (req: Request, res: Response) => {
    const produto = (req as any).produto as ProductModel.Product;
    const files = (req.files as Express.Multer.File[] | undefined) || [];

    const back = '/produtos/dashboard';
    if (files.length === 0) {
      return res.redirect(back + '?error=' + encodeURIComponent('Selecione ao menos uma imagem.'));
    }

    const existingCount = ProductImageModel.countByProduct(produto.id);
    const remaining = ProductImageModel.MAX_IMAGES_PER_PRODUCT - existingCount;
    if (remaining <= 0) {
      unlinkMulterFiles(files);
      return res.redirect(
        back +
        '?error=' +
        encodeURIComponent(
          `Este produto já tem o máximo de ${ProductImageModel.MAX_IMAGES_PER_PRODUCT} fotos.`,
        ),
      );
    }

    const accepted = files.slice(0, remaining);
    const ignored = files.length - accepted.length;
    if (ignored > 0) {
      unlinkMulterFiles(files.slice(remaining));
    }

    const stored = await Promise.all(accepted.map((file) => storeProductImage(file)));

    const hadMain = !!ProductImageModel.findMain(produto.id);
    stored.forEach((s, idx) => {
      const isMain = !hadMain && idx === 0;
      ProductImageModel.add(produto.id, s.imageUrl, isMain);
      if (isMain) {
        ProductModel.updateMainImageUrl(produto.id, s.imageUrl);
      }
    });

    const msg =
      `Adicionadas ${accepted.length} foto(s).` +
      (ignored > 0 ? ` ${ignored} ignorada(s) por exceder o limite.` : '');
    res.redirect(back + '?success=' + encodeURIComponent(msg));
  },
);

router.post('/:id/imagens/:imageId/principal', requireSeller, requireProductOwner, (req, res) => {
  const produto = (req as any).produto as ProductModel.Product;
  const imageIdParsed = positiveInteger.safeParse(req.params.imageId);
  if (!imageIdParsed.success) {
    return res.status(404).send('Imagem não encontrada.');
  }
  const image = ProductImageModel.findById(imageIdParsed.data);
  if (!image || image.productId !== produto.id) {
    return res.status(404).send('Imagem não encontrada.');
  }

  ProductImageModel.setMain(produto.id, imageIdParsed.data);
  ProductModel.updateMainImageUrl(produto.id, image.imageUrl);

  res.redirect('/produtos/dashboard?success=' + encodeURIComponent('Foto principal atualizada.'));
});

router.delete('/:id/imagens/:imageId', requireSeller, requireProductOwner, (req, res) => {
  const produto = (req as any).produto as ProductModel.Product;
  const imageIdParsed = positiveInteger.safeParse(req.params.imageId);
  if (!imageIdParsed.success) {
    return res.status(404).send('Imagem não encontrada.');
  }
  const image = ProductImageModel.findById(imageIdParsed.data);
  if (!image || image.productId !== produto.id) {
    return res.status(404).send('Imagem não encontrada.');
  }

  ProductImageModel.remove(imageIdParsed.data);

  const newMain = ProductImageModel.findMain(produto.id) || ProductImageModel.findFirst(produto.id);
  ProductModel.updateMainImageUrl(produto.id, newMain?.imageUrl ?? '');

  res.redirect('/produtos/dashboard?success=' + encodeURIComponent('Foto removida.'));
});

router.get('/:id', async (req, res) => {
  const idParsed = positiveInteger.safeParse(req.params.id);
  if (!idParsed.success) {
    return res.status(404).render('product-details', {
      produto: null,
      vendedor: null,
      comentarios: [],
      imagens: [],
      mainImage: null,
      commentError: null,
      currentUser: req.session.user,
    });
  }

  const produto = ProductModel.findById(idParsed.data);
  if (!produto)
    return res.status(404).render('product-details', {
      produto: null,
      vendedor: null,
      comentarios: [],
      imagens: [],
      mainImage: null,
      commentError: null,
      currentUser: req.session.user,
    });

  const vendedor = UserModel.findById(produto.userId);
  const imagens = ProductImageModel.findByProduct(produto.id);
  const mainImage = imagens.find((i) => i.isMain) || imagens[0] || null;

  const comentarios = CommentModel.findByProduct(produto.id).map((c) => {
    const autor = UserModel.findById(c.userId);
    const likesCount = CommentLikeModel.count(c.id);
    const likedByCurrentUser = req.session.user
      ? CommentLikeModel.isLikedByUser(c.id, req.session.user.id)
      : false;
    return { ...c, autor, likesCount, likedByCurrentUser };
  });

  const commentError = typeof req.query.commentError === 'string' ? req.query.commentError : null;

  res.render('product-details', {
    produto,
    vendedor,
    imagens,
    mainImage,
    comentarios,
    commentError,
    currentUser: req.session.user,
  });
});

export default router;
