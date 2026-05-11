import { Request, Response } from 'express';
import * as UserModel from '../models/UserModel';
import { sendVerificationEmail } from '../services/emailService';

const ALLOWED_SIGNUP_ROLES = ['comprador', 'vendedor'] as const;

type SignupForm = {
  givenName: string;
  familyName: string;
  email: string;
  role: string;
};

function renderForm(res: Response, error: string | null, form: Partial<SignupForm>): void {
  res.render('signup', {
    error,
    form: {
      givenName: form.givenName ?? '',
      familyName: form.familyName ?? '',
      email: form.email ?? '',
      role: form.role ?? 'comprador',
    },
  });
}

export function showSignupForm(_req: Request, res: Response): void {
  renderForm(res, null, {});
}

export async function submitSignup(req: Request, res: Response): Promise<void> {
  const givenName = String(req.body.givenName ?? '').trim();
  const familyName = String(req.body.familyName ?? '').trim();
  const email = String(req.body.email ?? '').trim();
  const password = String(req.body.password ?? '');
  const confirmPassword = String(req.body.confirmPassword ?? '');
  const roleRaw = String(req.body.role ?? '');

  const form: SignupForm = { givenName, familyName, email, role: roleRaw };

  if (!givenName || !familyName) {
    renderForm(res, 'Informe nome e sobrenome.', form);
    return;
  }

  const name = `${givenName} ${familyName}`.trim();

  if (!givenName || !/^[A-Za-zÀ-ÿ'\- ]{2,}$/.test(givenName)) {
    renderForm(res, 'Nome deve conter apenas letras e ter pelo menos 2 caracteres.', form);
    return;
  }
  if (!familyName || !/^[A-Za-zÀ-ÿ'\- ]{2,}$/.test(familyName)) {
    renderForm(res, 'Sobrenome deve conter apenas letras e ter pelo menos 2 caracteres.', form);
    return;
  }
  if (givenName.length > 40 || familyName.length > 40) {
    renderForm(res, 'Nome e sobrenome devem ter no máximo 40 caracteres.', form);
    return;
  }

  if (!email || email.length > 80 || !/^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/.test(email)) {
    renderForm(res, 'Informe um e-mail válido (máx. 80 caracteres).', form);
    return;
  }

  if (!password || password.length < 8 ||
    !/[A-Za-z]/.test(password) ||
    !/\d/.test(password) ||
    !/[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/.test(password)
  ) {
    renderForm(
      res,
      'A senha deve ter pelo menos 8 caracteres, incluindo letra, número e caractere especial.',
      form
    );
    return;
  }
  if (password.length > 64) {
    renderForm(res, 'A senha deve ter no máximo 64 caracteres.', form);
    return;
  }
  if (!confirmPassword) {
    renderForm(res, 'Confirme a senha.', form);
    return;
  }
  if (password !== confirmPassword) {
    renderForm(res, 'A confirmação da senha não confere.', form);
    return;
  }

  if (!ALLOWED_SIGNUP_ROLES.includes(roleRaw as (typeof ALLOWED_SIGNUP_ROLES)[number])) {
    renderForm(
      res,
      'Selecione um tipo de conta válido (comprador ou vendedor).',
      form,
    );
    return;
  }

  const role = roleRaw as 'comprador' | 'vendedor';

  if (UserModel.findByEmail(email)) {
    renderForm(res, 'Já existe uma conta com este e-mail.', form);
    return;
  }

  let userId: number;
  try {
    userId = UserModel.createUser({ name, email, plainPassword: password, role });
  } catch (err: unknown) {
    const code = err && typeof err === 'object' && 'code' in err ? String((err as { code: string }).code) : '';
    const msg = err instanceof Error ? err.message : '';
    if (code === 'SQLITE_CONSTRAINT_UNIQUE' || msg.includes('UNIQUE constraint failed')) {
      renderForm(res, 'Já existe uma conta com este e-mail.', form);
      return;
    }
    throw err;
  }

  const normalizedEmail = email.trim().toLowerCase();
  try {
    const verificationCode = UserModel.issueNewVerificationCodeForUserId(userId);    
    await sendVerificationEmail(normalizedEmail, verificationCode);
    res.redirect(`/verify-email.html?email=${encodeURIComponent(normalizedEmail)}&sent=1`);
  } catch (e) {
    console.error(e);
    res.redirect(`/verify-email.html?email=${encodeURIComponent(normalizedEmail)}&sent=0&mailerror=1`);
  }
}
