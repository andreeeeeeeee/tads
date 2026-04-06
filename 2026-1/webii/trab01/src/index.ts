import dotenv from 'dotenv';
dotenv.config();

import express from 'express';
import session from 'express-session';

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

import * as AdminController from './controllers/AdminController';
import * as HomeController from './controllers/HomeController';
import * as LogController from './controllers/LogController';
import * as LoginController from './controllers/LoginController';
import * as SignupController from './controllers/SignupController';
import * as VerifyEmailController from './controllers/VerifyEmailController';
import { auditLog } from './middleware/auditLog';
import { requireAuth, requireRole } from './middleware/requireAuth';
import { requireGuest } from './middleware/requireGuest';

const app = express();
const port = process.env.PORT || 3333;

app.set('view engine', 'ejs');
app.set('views', './src/views');

app.use(express.urlencoded({ extended: true }));

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

app.use((req, res, next) => {

  req.session.urls = req.session.urls || [];
  (req.session.urls as any).push(req.url);

  console.log({
    id_sessao: req.sessionID,
    sessao: req.session
  })
  next();
});

app.use(auditLog());

app.get('/signup', requireGuest, SignupController.showSignupForm);
app.get('/signup.html', requireGuest, SignupController.showSignupForm);
app.post('/signup', requireGuest, (req, res, next) => {
  void SignupController.submitSignup(req, res).catch(next);
});

app.get('/verify-email', VerifyEmailController.show);
app.get('/verify-email.html', VerifyEmailController.show);
app.post('/verify-email', VerifyEmailController.submit);
app.post('/verify-email/resend', (req, res, next) => {
  void VerifyEmailController.resend(req, res).catch(next);
});

app.get('/login', requireGuest, LoginController.show);
app.get('/login.html', requireGuest, LoginController.show);
app.post('/login', requireGuest, LoginController.submit);
app.post('/logout', LoginController.logout);

app.get('/index.html', (_req, res) => {
  res.render('index');
});
app.get('/categories.html', (_req, res) => {
  res.render('categories');
});
app.get('/product-details.html', (_req, res) => {
  res.render('product-details');
});

app.get('/cart.html', requireAuth, requireRole('comprador'), (_req, res) => {
  res.render('cart');
});
app.get('/checkout.html', requireAuth, requireRole('comprador'), (_req, res) => {
  res.render('checkout');
});
app.get('/orders.html', requireAuth, requireRole('comprador'), (_req, res) => {
  res.render('orders');
});

app.get('/seller-dashboard.html', requireAuth, requireRole('vendedor'), (_req, res) => {
  res.render('seller-dashboard');
});

app.post(
  '/admin/users/:id/active',
  requireAuth,
  requireRole('admin'),
  AdminController.setUserActive,
);
app.get('/admin-dashboard.html', requireAuth, requireRole('admin'), AdminController.showDashboard);

app.get('/logs', requireAuth, requireRole('admin'), LogController.getLogs);

app.get('/', HomeController.index);

app.listen(port, () => {
  console.log(`Servidor rodando em http://localhost:${port}`);
});
