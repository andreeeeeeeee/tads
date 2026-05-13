import { NextFunction, Request, Response, Router } from 'express';
import multer from 'multer';
import path from 'path';
import * as CommentLikeModel from '../models/CommentLikeModel';
import * as CommentModel from '../models/CommentModel';
import * as ProductModel from '../models/ProductModel';
import * as UserModel from '../models/UserModel';
import { unlinkMulterFile, zodIssues } from '../validation/helpers';
import { commentContentSchema, positiveInteger } from '../validation/schemas';

const router = Router();

const upload = multer({
  dest: path.join(__dirname, '../../uploads/comments'),
  limits: { fileSize: 2 * 1024 * 1024 }, // 2MB
  fileFilter: (_, file, cb) => {
    if (!file.mimetype.startsWith('image/'))
      return cb(new Error('Apenas imagens são permitidas.'));

    cb(null, true);
  },
});

function requireAuth(req: Request, res: Response, next: NextFunction) {
  if (!req.session.user) {
    if (req.accepts('html')) {
      const dest = encodeURIComponent(req.originalUrl.split('?')[0] || '/');
      return res.redirect(`/login?next=${dest}`);
    }
    return res.status(401).json({ message: 'É necessário estar autenticado.' });
  }
  next();
}

router.get('/produto/:productId', (req, res) => {
  const pid = positiveInteger.safeParse(req.params.productId);
  if (!pid.success) {
    return res.status(400).json({ message: 'ID de produto inválido.' });
  }
  const comentarios = CommentModel.findByProduct(pid.data);
  const comentariosComAutor = comentarios.map((c) => {
    const autor = UserModel.findById(c.userId);
    return { ...c, autor };
  });
  res.json(comentariosComAutor);
});

router.post('/produto/:productId', requireAuth, upload.single('image'), (req: Request, res: Response) => {
  const pid = positiveInteger.safeParse(req.params.productId);
  if (!pid.success) {
    unlinkMulterFile(req.file);
    return res.redirect('/index');
  }

  const product = ProductModel.findById(pid.data);
  if (!product) {
    unlinkMulterFile(req.file);
    return res.redirect('/index');
  }

  const contentParsed = commentContentSchema.safeParse(req.body);
  if (!contentParsed.success) {
    unlinkMulterFile(req.file);
    return res.redirect(
      `/produtos/${pid.data}?commentError=${encodeURIComponent(zodIssues(contentParsed.error).join(' '))}`,
    );
  }

  const user = req.session.user;
  if (!user) {
    unlinkMulterFile(req.file);
    return res.redirect('/login');
  }

  let imageUrl = '';
  if (req.file) imageUrl = '/uploads/comments/' + req.file.filename;

  CommentModel.create({
    productId: pid.data,
    userId: user.id,
    content: contentParsed.data.content,
    imageUrl,
  });
  res.redirect('/produtos/' + pid.data);
});

router.put('/:id', requireAuth, upload.single('image'), (req: Request, res: Response) => {
  const idParsed = positiveInteger.safeParse(req.params.id);
  if (!idParsed.success) {
    unlinkMulterFile(req.file);
    return res.status(400).json({ message: 'ID inválido.' });
  }

  const user = req.session.user;
  if (!user) {
    unlinkMulterFile(req.file);
    return res.status(401).json({ message: 'Não autenticado.' });
  }

  const comment = CommentModel.findById(idParsed.data);
  if (!comment) {
    unlinkMulterFile(req.file);
    return res.status(404).json({ message: 'Comentário não encontrado.' });
  }
  if (comment.userId !== user.id) {
    unlinkMulterFile(req.file);
    return res.status(403).json({ message: 'Apenas o autor pode editar.' });
  }

  const contentParsed = commentContentSchema.safeParse(req.body);
  if (!contentParsed.success) {
    unlinkMulterFile(req.file);
    return res.redirect(
      `/produtos/${comment.productId}?commentError=${encodeURIComponent(zodIssues(contentParsed.error).join(' '))}`,
    );
  }

  let imageUrl = comment.imageUrl;
  if (req.file) imageUrl = '/uploads/comments/' + req.file.filename;

  CommentModel.update(idParsed.data, contentParsed.data.content, imageUrl);
  res.redirect('/produtos/' + comment.productId);
});

router.delete('/:id', requireAuth, (req: Request, res: Response) => {
  const idParsed = positiveInteger.safeParse(req.params.id);
  if (!idParsed.success) return res.status(400).json({ message: 'ID inválido.' });

  const user = req.session.user;
  if (!user) return res.status(401).json({ message: 'Não autenticado.' });
  const comment = CommentModel.findById(idParsed.data);
  if (!comment) return res.status(404).json({ message: 'Comentário não encontrado.' });
  if (comment.userId !== user.id && user.role !== 'admin')
    return res.status(403).json({ message: 'Apenas o autor ou admin pode excluir.' });

  CommentModel.remove(idParsed.data);
  res.redirect('/produtos/' + comment.productId);
});

router.post('/:id', requireAuth, (req: Request, res: Response, next) => {
  if (req.body._method && req.body._method.toUpperCase() === 'DELETE') {
    req.method = 'DELETE';
    return next();
  }
  res.status(405).send('Método não permitido');
});

router.post('/:id/like', requireAuth, (req, res) => {
  const idParsed = positiveInteger.safeParse(req.params.id);
  if (!idParsed.success) return res.redirect('/index');

  const userId = req.session.user?.id;
  if (!userId) return res.redirect('/login');

  const comment = CommentModel.findById(idParsed.data);
  if (!comment) return res.redirect('/index');

  CommentLikeModel.like(idParsed.data, userId);
  res.redirect('/produtos/' + comment.productId);
});

router.post('/:id/unlike', requireAuth, (req, res) => {
  const idParsed = positiveInteger.safeParse(req.params.id);
  if (!idParsed.success) return res.redirect('/index');

  const userId = req.session.user?.id;
  if (!userId) return res.redirect('/login');

  const comment = CommentModel.findById(idParsed.data);
  if (!comment) return res.redirect('/index');

  CommentLikeModel.unlike(idParsed.data, userId);
  res.redirect('/produtos/' + comment.productId);
});

export default router;
