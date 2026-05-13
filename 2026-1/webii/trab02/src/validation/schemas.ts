import { z } from 'zod';

/** UFs brasileiras (inclui DF). */
export const BR_UFS = [
  'AC',
  'AL',
  'AP',
  'AM',
  'BA',
  'CE',
  'DF',
  'ES',
  'GO',
  'MA',
  'MT',
  'MS',
  'MG',
  'PA',
  'PB',
  'PR',
  'PE',
  'PI',
  'RJ',
  'RN',
  'RS',
  'RO',
  'RR',
  'SC',
  'SP',
  'SE',
  'TO',
] as const;

const ufSchema = z.enum(BR_UFS);

const phoneRegex = /^[0-9()+\-\s.]{8,22}$/;

export const buyerProfileSchema = z.object({
  telefone: z
    .string()
    .trim()
    .min(8, 'Telefone muito curto.')
    .max(22, 'Telefone muito longo.')
    .regex(phoneRegex, 'Telefone: use apenas números e símbolos ()+- .'),
  endereco: z.string().trim().min(5, 'Endereço deve ter pelo menos 5 caracteres.').max(300, 'Endereço muito longo.'),
  cidade: z.string().trim().min(2, 'Informe a cidade.').max(80, 'Cidade muito longa.'),
  estado: z
    .string()
    .trim()
    .min(2)
    .max(2)
    .transform((s) => s.toUpperCase())
    .pipe(ufSchema),
  cep: z
    .string()
    .trim()
    .transform((s) => s.replace(/\D/g, ''))
    .pipe(z.string().length(8, { message: 'CEP deve ter 8 dígitos.' })),
  pagamento: z
    .string()
    .trim()
    .min(3, 'Descreva a forma de pagamento preferencial (mín. 3 caracteres).')
    .max(120, 'Forma de pagamento muito longa.'),
});

export const sellerProfileSchema = z.object({
  loja_nome: z.string().trim().min(2, 'Nome da loja muito curto.').max(100, 'Nome da loja muito longo.'),
  loja_descricao: z
    .string()
    .trim()
    .min(5, 'Descrição da loja muito curta.')
    .max(2000, 'Descrição muito longa.'),
  telefone: z
    .string()
    .trim()
    .min(8, 'Telefone muito curto.')
    .max(22, 'Telefone muito longo.')
    .regex(phoneRegex, 'Telefone: use apenas números e símbolos ()+- .'),
  cidade: z.string().trim().min(2, 'Informe a cidade.').max(80, 'Cidade muito longa.'),
  estado: z
    .string()
    .trim()
    .min(2)
    .max(2)
    .transform((s) => s.toUpperCase())
    .pipe(ufSchema),
  loja_categorias: z
    .string()
    .trim()
    .min(2, 'Informe categorias ou tipos de produto.')
    .max(200, 'Campo de categorias muito longo.'),
});

const money = z.preprocess((v) => {
  if (v === '' || v === undefined || v === null) return undefined;
  if (typeof v === 'number') return Number.isFinite(v) ? v : NaN;
  const s = String(v).trim().replace(/\s/g, '').replace(',', '.');
  const n = Number(s);
  return Number.isFinite(n) ? n : NaN;
}, z.number({ invalid_type_error: 'Preço inválido.' }).positive('Preço deve ser maior que zero.').max(1_000_000, 'Preço acima do limite permitido.'));

export const productCreateBodySchema = z.object({
  nome: z.string().trim().min(1, 'Informe o nome do produto.').max(120, 'Nome muito longo.'),
  descricao: z.string().trim().min(1, 'Informe a descrição.').max(2000, 'Descrição muito longa.'),
  categoria: z.string().trim().min(1, 'Informe a categoria.').max(80, 'Categoria muito longa.'),
  preco: money,
  estoque: z.coerce.number({ invalid_type_error: 'Estoque inválido.' }).int('Estoque deve ser um número inteiro.').min(0, 'Estoque não pode ser negativo.').max(1_000_000, 'Estoque acima do limite.'),
});

export const commentContentSchema = z.object({
  content: z.string().trim().min(1, 'O comentário não pode ficar vazio.').max(500, 'Comentário com no máximo 500 caracteres.'),
});

export const positiveInteger = z.coerce.number().int().positive();

export const loginBodySchema = z.object({
  email: z.string().trim().toLowerCase().email('Informe um e-mail válido.').max(120),
  password: z.string().min(1, 'Informe a senha.').max(128),
  next: z.string().optional(),
});

export const verifyEmailSubmitSchema = z.object({
  email: z.string().trim().toLowerCase().email('E-mail inválido.').max(120),
  code: z
    .string()
    .trim()
    .regex(/^\d{6}$/, 'O código deve ter exatamente 6 dígitos.'),
});

export const verifyEmailResendSchema = z.object({
  email: z.string().trim().toLowerCase().email('E-mail inválido.').max(120),
});

export const adminUserIdParamSchema = z.object({
  id: z.coerce.number().int().positive(),
});

const signupNamePart = z
  .string()
  .trim()
  .min(2, 'Nome deve ter pelo menos 2 caracteres.')
  .max(40, 'Nome com no máximo 40 caracteres.')
  .regex(/^[A-Za-zÀ-ÿ'\- ]+$/, 'Use apenas letras, espaços, apóstrofo ou hífen.');

export const signupSubmitSchema = z
  .object({
    givenName: signupNamePart,
    familyName: signupNamePart,
    email: z.string().trim().toLowerCase().email('Informe um e-mail válido.').max(80, 'E-mail muito longo.'),
    password: z
      .string()
      .min(8, 'A senha deve ter pelo menos 8 caracteres.')
      .max(64, 'A senha deve ter no máximo 64 caracteres.')
      .regex(/[A-Za-z]/, 'A senha deve incluir pelo menos uma letra.')
      .regex(/\d/, 'A senha deve incluir pelo menos um número.')
      .regex(
        /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/,
        'A senha deve incluir pelo menos um caractere especial.',
      ),
    confirmPassword: z.string(),
    role: z.enum(['comprador', 'vendedor'], {
      message: 'Selecione um tipo de conta válido (comprador ou vendedor).',
    }),
  })
  .refine((d) => d.password === d.confirmPassword, {
    message: 'A confirmação da senha não confere.',
    path: ['confirmPassword'],
  });
