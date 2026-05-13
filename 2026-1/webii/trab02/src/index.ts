import dotenv from 'dotenv';
dotenv.config();

import express from 'express';
import session from 'express-session';
import methodOverride from 'method-override';

declare module 'express-session' {
  interface SessionData {
    urls?: string[];
    user?: {
      id: number;
      name: string;
      email: string;
      role: 'admin' | 'comprador' | 'vendedor';
    };
  }
}

import AdminController from './controllers/AdminController';
import BuyerController from './controllers/BuyerController';
import CommentController from './controllers/CommentController';
import HomeController from './controllers/HomeController';
import LogController from './controllers/LogController';
import LoginController from './controllers/LoginController';
import ProductsController from './controllers/ProductsController';
import SellerController from './controllers/SellerController';
import SellerProfileController from './controllers/SellerProfileController';
import SignupController from './controllers/SignupController';
import VerifyEmailController from './controllers/VerifyEmailController';
import { auditLog } from './middleware/auditLog';

const app = express();
const port = process.env.PORT || 3333;

app.set('view engine', 'ejs');
app.set('views', './src/views');

app.use(express.urlencoded({ extended: true }));
app.use(methodOverride('_method'));

const sessionSecret =
  process.env.SESSION_SECRET ||
  (process.env.STAGE !== 'prod' ? 'dev-only-insecure-session-secret' : '');

if (!sessionSecret)
  throw new Error(
    'SESSION_SECRET não está definido. Crie um arquivo .env com SESSION_SECRET=<valor seguro> ou defina a variável no ambiente.',
  );

if (!process.env.SESSION_SECRET && process.env.STAGE !== 'prod')
  console.warn(
    '[sessão] SESSION_SECRET ausente: usando segredo fixo de desenvolvimento. Defina SESSION_SECRET no .env antes de produção.',
  );

app.use(
  session({
    secret: sessionSecret,
    saveUninitialized: true,
    cookie: {
      // secure: true // em prod
    },
  }),
);

app.use((req, res, next) => {
  res.locals.currentUser = req.session.user ?? null;
  next();
});

app.use((req, _, next) => {
  req.session.urls = req.session.urls || [];
  (req.session.urls as any).push(req.url);

  console.log({
    id_sessao: req.sessionID,
    sessao: req.session,
  });
  next();
});

app.use(auditLog());
app.use('/uploads', express.static('uploads'));
app.use('/uploads/comments', express.static('uploads/comments'));

app.use('/comprador', BuyerController);
app.use('/vendedor', SellerProfileController);
app.use('/produtos', ProductsController);
app.use('/comentarios', CommentController);

app.use(HomeController);
app.use(SignupController);
app.use(LoginController);
app.use(VerifyEmailController);
app.use(SellerController);
app.use(AdminController);
app.use(LogController);

app.listen(port, () => {
  console.log(`Servidor rodando em http://localhost:${port}`);
});
