# Trabalho III - Backend do Controle Financeiro Pessoal

Backend em Node.js + TypeScript para controle financeiro pessoal, organizado em camadas (domain, application, infrastructure, interfaces/http) conforme as Aulas 09–12.

## Funcionalidades

- Cadastro e login com JWT
- Categorias financeiras por usuário
- Lançamentos (receitas e despesas) com filtros por mês, ano e categoria
- Relatórios: saldo mensal e resumo por categoria
- Marcação de despesas como pagas

## Pré-requisitos

- Node.js 20+
- npm

## Instalação

```bash
npm install
```

## Execução

```bash
# Desenvolvimento (hot reload)
npm run dev

# Build e produção
npm run build
npm start
```

O servidor sobe na porta `3000` por padrão. Use a variável `PORT` para alterar.

## Testes

```bash
# Suite completa (unitários + integração)
npm test

# Apenas unitários
npm run test:unit

# Apenas integração
npm run test:integration

# Modo watch
npm run test:watch
```

## Variáveis de ambiente

| Variável     | Descrição                          | Padrão       |
| ------------ | ---------------------------------- | ------------ |
| `PORT`       | Porta do servidor HTTP             | `3000`       |
| `JWT_SECRET` | Segredo para assinatura dos tokens | `dev-secret` |

## Estrutura

```text
src/
  main/           # app, routes, container, server
  modules/
    auth/         # registro e login
    users/        # entidade e repositório de usuário
    categories/   # categorias financeiras
    transactions/ # lançamentos
    reports/      # saldo e resumo por categoria
  shared/         # middleware, erros compartilhados
tests/
  unit/           # domínio e casos de uso
  integration/    # rotas HTTP
```

## Rotas principais

| Método | Rota                        | Auth |
| ------ | --------------------------- | ---- |
| POST   | `/auth/register`            | Não  |
| POST   | `/auth/login`               | Não  |
| POST   | `/categories`               | Sim  |
| GET    | `/categories`               | Sim  |
| POST   | `/transactions`             | Sim  |
| GET    | `/transactions`             | Sim  |
| PATCH  | `/transactions/:id/pay`     | Sim  |
| GET    | `/reports/monthly-balance`  | Sim  |
| GET    | `/reports/category-summary` | Sim  |

Rotas autenticadas exigem header `Authorization: Bearer <token>`.
