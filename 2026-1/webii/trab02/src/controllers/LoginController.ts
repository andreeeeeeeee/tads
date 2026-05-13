import { Request, Response, Router } from 'express';
import { requireGuest } from '../middleware/requireGuest';
import * as UserModel from '../models/UserModel';
import { zodIssues } from '../validation/helpers';
import { loginBodySchema } from '../validation/schemas';

export type LoginForm = { email: string; next: string };

function sanitizeNext(raw: unknown): string {
  let s = '';
  if (typeof raw === 'string') s = raw.trim();
  else if (Array.isArray(raw) && typeof raw[0] === 'string') s = raw[0].trim();

  if (!s.startsWith('/') || s.startsWith('//') || s.includes('://')) return '/';

  return s;
}

function renderLogin(
  res: Response,
  opts: {
    registered?: boolean;
    deactivated?: boolean;
    verified?: boolean;
    alreadyVerified?: boolean;
    error?: string | null;
    form?: Partial<LoginForm>;
    verifyEmailLink?: string | null;
  },
): void {
  const form = {
    email: opts.form?.email ?? '',
    next: sanitizeNext(opts.form?.next),
  };
  res.render('login', {
    registered: Boolean(opts.registered),
    deactivated: Boolean(opts.deactivated),
    verified: Boolean(opts.verified),
    alreadyVerified: Boolean(opts.alreadyVerified),
    error: opts.error ?? null,
    form,
    verifyEmailLink: opts.verifyEmailLink ?? null,
  });
}

function show(req: Request, res: Response): void {
  if (req.session.user) {
    res.redirect('/');
    return;
  }
  renderLogin(res, {
    registered: req.query.registered === '1',
    deactivated: req.query.deactivated === '1',
    verified: req.query.verified === '1',
    alreadyVerified: req.query.already === 'verified',
    error: null,
    form: {
      email: '',
      next: sanitizeNext(req.query.next),
    },
  });
}

function submit(req: Request, res: Response): void {
  const parsed = loginBodySchema.safeParse({
    email: req.body.email,
    password: req.body.password,
    next: req.body.next ?? req.query.next,
  });

  const next = sanitizeNext(
    parsed.success ? parsed.data.next : (req.body.next ?? req.query.next),
  );
  const form: LoginForm = {
    email: parsed.success ? parsed.data.email : String(req.body.email ?? '').trim(),
    next,
  };

  if (!parsed.success) {
    renderLogin(res, { error: zodIssues(parsed.error).join(' '), form });
    return;
  }

  const { email, password } = parsed.data;
  const result = UserModel.authenticate(email, password);

  if (!result.ok) {
    if (result.reason === 'inactive') {
      renderLogin(res, {
        error: 'Esta conta foi desativada. Entre em contato com o suporte.',
        form,
      });
      return;
    }
    if (result.reason === 'unverified') {
      const enc = encodeURIComponent(email.trim().toLowerCase());
      renderLogin(res, {
        error: 'Valide seu e-mail com o código enviado antes de entrar.',
        form,
        verifyEmailLink: `/verify-email?email=${enc}`,
      });
      return;
    }
    renderLogin(res, {
      error: 'E-mail ou senha incorretos.',
      form,
    });
    return;
  }

  const u = result.user;
  req.session.user = {
    id: u.id,
    name: u.name,
    email: u.email,
    role: u.role,
  };

  res.redirect(next);
}

function logout(req: Request, res: Response): void {
  req.session.destroy((err) => {
    if (err) {
      res.status(500).send('Não foi possível encerrar a sessão.');
      return;
    }
    res.redirect('/login');
  });
}

const router = Router();

router.get('/login', requireGuest, show);
router.get('/login', requireGuest, show);
router.post('/login', requireGuest, submit);
router.post('/logout', logout);

export default router;
