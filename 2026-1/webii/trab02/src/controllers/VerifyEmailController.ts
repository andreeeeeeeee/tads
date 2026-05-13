import { Request, Response, Router } from 'express';
import * as UserModel from '../models/UserModel';
import { sendVerificationEmail, verificationTtlMinutes } from '../services/emailService';
import { verifyEmailResendSchema, verifyEmailSubmitSchema } from '../validation/schemas';

const errorMessages: Record<string, string> = {
  expired: 'O código expirou. Solicite um novo código abaixo.',
  invalid: 'Código inválido. Confira os números e tente novamente.',
  notfound: 'E-mail não encontrado.',
  empty: 'Informe e-mail e código.',
  inactive: 'Esta conta está desativada.',
  send: 'Não foi possível enviar o e-mail. Configure SMTP ou tente mais tarde.',
};

function show(req: Request, res: Response): void {
  const email = typeof req.query.email === 'string' ? req.query.email.trim().toLowerCase() : '';
  const errKey = typeof req.query.error === 'string' ? req.query.error : '';
  const error = errKey && errorMessages[errKey] ? errorMessages[errKey] : null;

  res.render('verify-email', {
    email,
    error,
    sent: req.query.sent === '1',
    mailError: req.query.mailerror === '1',
    ttlMinutes: verificationTtlMinutes(),
  });
}

function submit(req: Request, res: Response): void {
  const parsed = verifyEmailSubmitSchema.safeParse(req.body);
  if (!parsed.success) {
    const email = String(req.body.email ?? '').trim().toLowerCase();
    const q = new URLSearchParams();
    if (email) q.set('email', email);
    q.set('error', 'invalid');
    res.redirect(`/verify-email?${q.toString()}`);
    return;
  }

  const { email, code } = parsed.data;
  const result = UserModel.verifyEmailWithCode(email, code);
  if (result.ok) {
    res.redirect('/login?verified=1');
    return;
  }

  const q = new URLSearchParams({ email });
  if (result.reason === 'already_verified') {
    res.redirect('/login?already=verified');
    return;
  }
  q.set(
    'error',
    result.reason === 'expired'
      ? 'expired'
      : result.reason === 'not_found'
        ? 'notfound'
        : 'invalid',
  );

  res.redirect(`/verify-email?${q.toString()}`);
}

async function resend(req: Request, res: Response): Promise<void> {
  const parsed = verifyEmailResendSchema.safeParse(req.body);
  if (!parsed.success) {
    res.redirect('/verify-email');
    return;
  }

  const { email } = parsed.data;
  const user = UserModel.findByEmail(email);
  if (!user) {
    res.redirect(`/verify-email?email=${encodeURIComponent(email)}&error=notfound`);
    return;
  }
  if (user.email_verified) {
    res.redirect('/login?already=verified');
    return;
  }
  if (!user.active) {
    res.redirect(`/verify-email?email=${encodeURIComponent(email)}&error=inactive`);
    return;
  }

  try {
    const code = UserModel.issueNewVerificationCodeForUserId(user.id);
    await sendVerificationEmail(user.email, code);
    res.redirect(`/verify-email?email=${encodeURIComponent(email)}&sent=1`);
  } catch (e) {
    console.error(e);
    res.redirect(`/verify-email?email=${encodeURIComponent(email)}&error=send`);
  }
}

const router = Router();

router.get('/verify-email', show);
router.get('/verify-email', show);
router.post('/verify-email', submit);
router.post('/verify-email/resend', (req, res, next) => {
  void resend(req, res).catch(next);
});

export default router;
