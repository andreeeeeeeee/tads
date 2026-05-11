# Sistema de Usuários - Template 1

## Instruções de Instalação e Execução

1. **Pré-requisitos:**
   - Node.js 18+
   - npm 9+

2. **Instalação:**

   ```bash
   npm install
   ```

3. **Execução:**

   ```bash
   npm start
   ```

   O servidor estará disponível em <http://localhost:3333>

4. **Banco de dados:**
   - O banco SQLite será criado automaticamente como `banco.db` na raiz do projeto.

## Configuração do arquivo `.env`

Crie um arquivo `.env` na raiz do projeto com o seguinte conteúdo (exemplo para Gmail):

``` .env
SESSION_SECRET="sua-chave-secreta"
SMTP_HOST="smtp.gmail.com"
SMTP_PORT=587
SMTP_SECURE=0
SMTP_USER="seu-email@gmail.com"
SMTP_PASS="sua-senha-de-app"
EMAIL_FROM="MarketMVP <seu-email@gmail.com>"
```

- `SESSION_SECRET`: Uma string secreta para proteger as sessões.
- `SMTP_HOST`, `SMTP_PORT`, `SMTP_SECURE`, `SMTP_USER`, `SMTP_PASS`, `EMAIL_FROM`: Dados do servidor SMTP para envio de e-mails (exemplo acima para Gmail). Para Gmail, use uma senha de app.

> **Importante:** Para Gmail, gere uma senha de app em <https://myaccount.google.com/apppasswords>

## Usuários de Teste

- **Administrador:**
  - E-mail: `admin@teste.com`
  - Senha: `Admin@123`
  - Perfil: admin

- **Comprador:**
  - E-mail: `comprador@teste.com`
  - Senha: `Comprador@123`
  - Perfil: comprador

- **Vendedor:**
  - E-mail: `vendedor@teste.com`
  - Senha: `Vendedor@123`
  - Perfil: vendedor

## Funcionalidades Implementadas

- Cadastro de novos usuários com validação de e-mail.
- Login seguro com sessão.
- Perfis: administrador, comprador e vendedor, com controle de acesso por perfil.
- Área administrativa para gestão de usuários (apenas admin pode desativar contas).
- Validação de e-mail por código único enviado ao usuário.
- Auditoria de logs: todas as ações não-GET são registradas e podem ser consultadas apenas por administradores.
- Mensagens de erro e sucesso claras para o usuário.
- Organização do código em camadas (rotas, controladores, serviços/modelos).

---
