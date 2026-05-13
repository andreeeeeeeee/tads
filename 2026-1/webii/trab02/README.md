# MarketMVP — Trabalho II (Web II)

Evolução do marketplace iniciado no Trabalho I e na Aula 08. O sistema mantém autenticação e separação por perfis (admin / comprador / vendedor) e adiciona perfis estendidos, página de detalhes do produto com galeria de fotos e sistema de comentários com curtidas e foto opcional.

## Instruções de Instalação e Execução

1. **Pré-requisitos:**
   - Node.js 18+
   - npm 9+

2. **Instalação:**

   ```bash
   npm install
   ```

3. **Configuração do `.env`**
   Crie um arquivo `.env` na raiz do projeto:

   ```env
   SESSION_SECRET="sua-chave-secreta"

   # Necessário para envio do e-mail de verificação no signup
   SMTP_HOST="smtp.gmail.com"
   SMTP_PORT=587
   SMTP_SECURE=0
   SMTP_USER="seu-email@gmail.com"
   SMTP_PASS="sua-senha-de-app"
   EMAIL_FROM="MarketMVP <seu-email@gmail.com>"

   # Opcional: pular a verificação de e-mail no login (útil em testes)
   # ALLOW_UNVERIFIED_LOGIN=1
   ```

   > Para Gmail use uma senha de app: <https://myaccount.google.com/apppasswords>

4. **Execução em desenvolvimento (recomendado)**

   ```bash
   npm run dev
   ```

   O servidor sobe em <http://localhost:3333> e reinicia a cada alteração.

5. **Execução em produção (build + start)**

   ```bash
   npm run build
   npm start
   ```

6. **Banco de dados**
   O banco SQLite (`banco.db`) é criado automaticamente na raiz do projeto na primeira execução. As tabelas (`users`, `products`, `product_images`, `comments`, `comment_likes`, `logs`) são criadas via `CREATE TABLE IF NOT EXISTS`.

   Os arquivos enviados (fotos de produtos e comentários) ficam em `uploads/products/` e `uploads/comments/`.

## Usuários de Teste

| Perfil    | E-mail                | Senha           |
| --------- | --------------------- | --------------- |
| Admin     | `admin@teste.com`     | `Admin@123`     |
| Comprador | `comprador@teste.com` | `Comprador@123` |
| Vendedor  | `vendedor@teste.com`  | `Vendedor@123`  |

> Os usuários de teste vêm do trabalho anterior. Caso o seu `banco.db` não traga essa carga inicial, é possível criar contas pela tela de cadastro (`/signup`) e habilitá-las definindo `ALLOW_UNVERIFIED_LOGIN=1` no `.env`.

## Funcionalidades Implementadas

### Já existentes (mantidas do Trabalho I + Aula 08)

- Cadastro de usuários com verificação de e-mail por código.
- Login com sessão e logout.
- Três perfis: **admin**, **comprador** e **vendedor**, com `requireAuth` / `requireRole` para controle de acesso.
- Painel administrativo (ativar/desativar contas) e visualização de logs de auditoria.
- Cadastro de produtos pelo vendedor.

### Adicionadas no Trabalho II

#### Perfil do comprador

- Página privada `/comprador/perfil` exibindo telefone, endereço, cidade, estado, CEP e forma de pagamento preferencial.
- Edição via `/comprador/editar` (apenas o próprio comprador, garantido por `requireRole('comprador')` + `req.session.user.id`).
- Persistência em colunas dedicadas da tabela `users`.

#### Perfil do vendedor

- Página privada `/vendedor/perfil` para o próprio vendedor visualizar e editar seus dados.
- **Perfil público em `/vendedor/:id`** — acessível a qualquer visitante (inclusive não autenticado), exibindo nome da loja, descrição, telefone, cidade/estado, categorias e a lista dos produtos do vendedor.
- Na tela de detalhes do produto, o nome do vendedor é um link clicável que leva para esse perfil público.
- Cada produto fica associado ao vendedor (`products.user_id`) e a interface deixa claro de quem é o anúncio.

#### Múltiplas fotos por produto

- Tabela `product_images` (id, product_id, image_url, is_main, created_at).
- No cadastro, o vendedor pode enviar até **8 imagens** por produto e escolher qual será a **principal** (radio "Principal" sobre cada miniatura). Caso não escolha, a primeira foto é assumida como destaque.
- No painel do vendedor, cada produto exibe sua galeria com botões "Tornar principal" e "Remover" por foto, além de um formulário "Adicionar mais fotos" enquanto não atingir 8.
- A coluna legada `products.image_url` é mantida sincronizada com a foto principal para manter as listagens (home, dashboard) coerentes.
- Validação no backend: tipos `image/jpeg`, `image/png`, `image/webp` e `image/gif`, máximo de **5MB** por arquivo, limite de quantidade respeitado, mensagens de erro amigáveis em caso de violação (via `productImageUpload` em `src/config/upload.ts` + `handleUploadErrors`).

#### Tela de detalhes do produto

- Página `/produtos/:id` exibindo nome, descrição, categoria, preço, estoque, **galeria com imagem principal grande + miniaturas clicáveis** que trocam a foto destacada via JavaScript, informações do vendedor (com link para o perfil público) e área de comentários.
- Cada comentário mostra autor, data/hora e quantidade de curtidas recebidas.

#### Sistema de comentários

- Apenas usuários autenticados podem comentar (`requireAuth` no `CommentController`).
- Cada comentário guarda autor (`user_id`), produto (`product_id`), conteúdo, data/hora (`created_at`) e foto opcional.
- O autor pode **editar** (`PUT /comentarios/:id`) e **excluir** (`DELETE /comentarios/:id`) o próprio comentário; o **admin** pode excluir qualquer comentário.
- Comentários são listados ordenados por data (mais recentes primeiro), com identificação clara do autor e da data.

#### Sistema de curtidas

- Tabela `comment_likes` com restrição **`UNIQUE(comment_id, user_id)`**, garantindo no banco que um mesmo usuário só curte um comentário uma única vez.
- A camada de aplicação (`CommentLikeModel.like`) também trata a violação dessa restrição, garantindo a regra também no backend.
- O usuário pode remover sua curtida (`POST /comentarios/:id/unlike`).
- A quantidade de curtidas é exibida em cada comentário.

## Observações sobre as regras de comentário e curtida

- Para comentar, é necessário estar autenticado. Visitantes anônimos visualizam os comentários, mas o formulário não aparece.
- O autor do comentário pode editar o texto e/ou trocar a foto anexada; somente ele e o admin podem excluí-lo.
- O upload de foto no comentário é opcional, limitado a `image/*` e a 2MB (definido em `CommentController`).
- Curtidas são **idempotentes**: clicar em "Curtir" duas vezes não duplica o registro (a restrição `UNIQUE` no banco impede isso, e o método `like()` do model devolve `false` silenciosamente em caso de duplicata). Para retirar a curtida o usuário usa "Descurtir".
- A contagem de curtidas é atualizada em tempo real a cada renderização da página do produto (sem necessidade de campo desnormalizado).

## Validação de dados (Zod)

Os dados enviados por formulários e parâmetros sensíveis são validados no backend com **[Zod](https://zod.dev)** (`src/validation/schemas.ts`), com mensagens agregadas em listas ou query string quando faz sentido para a UX:

- **Cadastro** (`signupSubmitSchema`): nome/sobrenome, e-mail, senha (complexidade), confirmação e perfil (`comprador` | `vendedor`).
- **Login** (`loginBodySchema`): e-mail e senha; o parâmetro `next` continua passando por `sanitizeNext` contra open redirect.
- **Verificação de e-mail** (`verifyEmailSubmitSchema`, `verifyEmailResendSchema`): e-mail e código de 6 dígitos.
- **Perfil comprador** (`buyerProfileSchema`): telefone, endereço, cidade, **UF** (lista de UFs), **CEP** (8 dígitos), pagamento.
- **Perfil vendedor** (`sellerProfileSchema`): loja, descrição, telefone, cidade, UF, categorias.
- **Cadastro de produto** (`productCreateBodySchema`): nome, descrição, categoria, preço (positivo, máx. 1M), estoque inteiro não negativo; em caso de erro, os arquivos já gravados pelo multer são removidos do disco.
- **Comentários** (`commentContentSchema` + `positiveIdParam`): texto 1–500 caracteres; IDs de produto/comentário positivos; produto inexistente não cria comentário; falha na validação remove arquivo de imagem órfão do comentário, se houver.
- **Admin** (`adminUserIdParamSchema`): ID numérico positivo ao ativar/desativar usuário.

## Estrutura de Pastas

```text
src/
├── config/          # db.ts (schema + conexão SQLite) e upload.ts (multer p/ imagens de produto)
├── controllers/     # rotas de cada recurso (Buyer, SellerProfile, Products, Comment, ...)
├── middleware/      # requireAuth, requireRole, requireGuest, auditLog
├── models/          # acesso ao banco (User, Product, ProductImage, Comment, CommentLike, Log)
├── services/        # emailService, product-image-storage
├── validation/      # schemas Zod (schemas.ts) e helpers (helpers.ts)
├── views/           # templates EJS
└── index.ts         # bootstrap do Express, sessão e registro das rotas
```

`uploads/products/` e `uploads/comments/` armazenam os arquivos enviados.

## Mapa de Rotas Relevantes

| Método   | Rota                                       | Quem pode                           |
| -------- | ------------------------------------------ | ----------------------------------- |
| GET      | `/produtos/:id`                            | qualquer visitante                  |
| GET      | `/vendedor/:id`                            | qualquer visitante (perfil público) |
| GET/POST | `/vendedor/perfil` · `/vendedor/editar`    | vendedor (próprio)                  |
| GET/POST | `/comprador/perfil` · `/comprador/editar`  | comprador (próprio)                 |
| GET      | `/produtos/dashboard`                      | vendedor / admin                    |
| POST     | `/produtos/cadastrar`                      | vendedor / admin                    |
| POST     | `/produtos/:id/imagens`                    | dono do produto / admin             |
| POST     | `/produtos/:id/imagens/:imageId/principal` | dono do produto / admin             |
| DELETE   | `/produtos/:id/imagens/:imageId`           | dono do produto / admin             |
| POST     | `/comentarios/produto/:productId`          | autenticado                         |
| PUT      | `/comentarios/:id`                         | autor do comentário                 |
| DELETE   | `/comentarios/:id`                         | autor ou admin                      |
| POST     | `/comentarios/:id/like` · `/unlike`        | autenticado                         |
