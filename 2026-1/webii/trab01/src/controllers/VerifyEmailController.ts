import { Request, Response } from 'express';
import * as UserModel from '../models/UserModel';
import { sendVerificationEmail, verificationTtlMinutes } from '../services/emailService';

const errorMessages: Record<string, string> = {
  expired: 'O código expirou. Solicite um novo código abaixo.',
  invalid: 'Código inválido. Confira os números e tente novamente.',
  notfound: 'E-mail não encontrado.',
  empty: 'Informe e-mail e código.',
  inactive: 'Esta conta está desativada.',
  send: 'Não foi possível enviar o e-mail. Configure SMTP ou tente mais tarde.',
};

export function show(req: Request, res: Response): void {
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

export function submit(req: Request, res: Response): void {
  const email = String(req.body.email ?? '').trim().toLowerCase();
  const code = String(req.body.code ?? '');

  if (!email || !code.trim()) {
    const q = new URLSearchParams();
    email
      ? q.set('email', email)
      : q.set('error', 'empty');
    res.redirect(`/verify-email.html?${q.toString()}`);
    return;
  }

  const result = UserModel.verifyEmailWithCode(email, code);
  if (result.ok) {
    res.redirect('/login.html?verified=1');
    return;
  }

  const q = new URLSearchParams({ email });
  if (result.reason === 'already_verified') {
    res.redirect('/login.html?already=verified');
    return;
  }
  q.set('error',
    result.reason === 'expired'
      ? 'expired'
      : result.reason === 'not_found'
        ? 'notfound'
        : 'invalid');

  res.redirect(`/verify-email.html?${q.toString()}`);
}

export async function resend(req: Request, res: Response): Promise<void> {
  const email = String(req.body.email ?? '').trim().toLowerCase();
  if (!email) {
    res.redirect('/verify-email.html');
    return;
  }

  const user = UserModel.findByEmail(email);
  if (!user) {
    res.redirect(`/verify-email.html?email=${encodeURIComponent(email)}&error=notfound`);
    return;
  }
  if (user.email_verified) {
    res.redirect('/login.html?already=verified');
    return;
  }
  if (!user.active) {
    res.redirect(`/verify-email.html?email=${encodeURIComponent(email)}&error=inactive`);
    return;
  }

  try {
    const code = UserModel.issueNewVerificationCodeForUserId(user.id);
    await sendVerificationEmail(user.email, code);
    res.redirect(`/verify-email.html?email=${encodeURIComponent(email)}&sent=1`);
  } catch (e) {
    console.error(e);
    res.redirect(`/verify-email.html?email=${encodeURIComponent(email)}&error=send`);
  }
}
