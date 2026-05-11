import nodemailer from 'nodemailer';

function minutesToMs(min: number): number {
  return min * 60 * 1000;
}

export function verificationTtlMs(): number {
  const raw = Number(process.env.VERIFY_CODE_TTL_MINUTES || 20);
  const clamped = Math.min(Math.max(Number.isFinite(raw) ? raw : 20, 15), 30);
  return minutesToMs(clamped);
}

export function verificationTtlMinutes(): number {
  return Math.round(verificationTtlMs() / 60000);
}

export async function sendVerificationEmail(to: string, code: string): Promise<void> {  
  const host = process.env.SMTP_HOST;
  const minutes = verificationTtlMinutes();
  const text = `Seu código de verificação MarketMVP: ${code}\n\nEle expira em ${minutes} minutos. Se você não criou uma conta, ignore este e-mail.`;
  const html = `<p>Seu código de verificação <strong>MarketMVP</strong>:</p><p style="font-size:1.5rem;letter-spacing:0.2em;font-weight:bold">${code}</p><p>O código expira em <strong>${minutes} minutos</strong>.</p><p>Se você não criou uma conta, ignore este e-mail.</p>`;

  if (!host) {
    console.warn(
      `[email] SMTP_HOST não configurado — e-mail não enviado. Destino: ${to} | Código (apenas dev): ${code}`,
    );
    return;
  }

  const port = Number(process.env.SMTP_PORT || 587);
  const secure = process.env.SMTP_SECURE === '1' || port === 465;
  const user = process.env.SMTP_USER || '';
  const pass = process.env.SMTP_PASS || '';
  const from = process.env.EMAIL_FROM || user || 'noreply@marketmvp.local';

  const transporter = nodemailer.createTransport({
    host,
    port,
    secure,
    auth: user ? { user, pass } : undefined,
  });

  await transporter.sendMail({
    from,
    to,
    subject: 'MarketMVP — código de verificação de e-mail',
    text,
    html,
  });
}
