import { Request, Response, Router } from 'express';
import { requireGuest } from '../middleware/requireGuest';
import * as UserModel from '../models/UserModel';
import { sendVerificationEmail } from '../services/emailService';
import { zodIssues } from '../validation/helpers';
import { signupSubmitSchema } from '../validation/schemas';

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

function showSignupForm(_req: Request, res: Response): void {
  renderForm(res, null, {});
}

async function submitSignup(req: Request, res: Response): Promise<void> {
  const form: SignupForm = {
    givenName: String(req.body.givenName ?? ''),
    familyName: String(req.body.familyName ?? ''),
    email: String(req.body.email ?? ''),
    role: String(req.body.role ?? 'comprador'),
  };

  const parsed = signupSubmitSchema.safeParse(req.body);
  if (!parsed.success) {
    renderForm(res, zodIssues(parsed.error).join(' '), form);
    return;
  }

  const { givenName, familyName, email, password, role } = parsed.data;
  const name = `${givenName} ${familyName}`.trim();

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

  try {
    const verificationCode = UserModel.issueNewVerificationCodeForUserId(userId);
    await sendVerificationEmail(email, verificationCode);
    res.redirect(`/verify-email?email=${encodeURIComponent(email)}&sent=1`);
  } catch (e) {
    console.error(e);
    res.redirect(`/verify-email?email=${encodeURIComponent(email)}&sent=0&mailerror=1`);
  }
}

const router = Router();

router.get('/signup', requireGuest, showSignupForm);
router.get('/signup', requireGuest, showSignupForm);
router.post('/signup', requireGuest, (req, res, next) => {
  void submitSignup(req, res).catch(next);
});

export default router;
