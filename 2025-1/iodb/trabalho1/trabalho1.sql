DROP DATABASE IF EXISTS trabalho1;

CREATE DATABASE trabalho1;

\c trabalho1;

CREATE TABLE
  usuario (
    id SERIAL PRIMARY KEY,
    nome CHARACTER VARYING(100) NOT NULL,
    email CHARACTER VARYING(100) UNIQUE NOT NULL,
    senha CHARACTER VARYING(100) NOT NULL,
    data_nascimento DATE CHECK (
      EXTRACT(
        YEAR
        FROM
          data_nascimento
      ) >= 1900
    ) NOT NULL
  );

INSERT INTO
  usuario (nome, email, senha, data_nascimento)
VALUES
  (
    'André Bastilhos',
    '2024007145@aluno.riogrande.ifrs.edu.br',
    MD5('123'),
    '2005-03-26'
  );

CREATE TABLE
  conta (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER REFERENCES usuario (id),
    data_hora_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome_usuario TEXT NOT NULL UNIQUE
  );

INSERT INTO
  conta (usuario_id, nome_usuario)
VALUES
  (1, '@andre_bs_'),
  (1, '@andrefutebois');

CREATE TABLE
  publicacao (
    id SERIAL PRIMARY KEY,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    texto TEXT,
    arquivo_principal TEXT NOT NULL,
    latitude REAL,
    longitude REAL
  );

INSERT INTO
  publicacao (texto, arquivo_principal)
VALUES
  ('foto na agua', 'foto_na_agua.jpeg'),
  ('foto de paisagem', 'foto_de_paisagem.png'),
  ('dump trimestral', 'dump_trimestral.gif');

CREATE TABLE
  conta_publicacao (
    publicacao_id INTEGER REFERENCES publicacao (id),
    conta_id INTEGER REFERENCES conta (id),
    PRIMARY KEY (conta_id, publicacao_id)
  );

INSERT INTO
  conta_publicacao (conta_id, publicacao_id)
VALUES
  (1, 1),
  (2, 2),
  (2, 3);

CREATE TABLE
  comentario (
    id SERIAL PRIMARY KEY,
    texto TEXT NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    publicacao_id INTEGER REFERENCES publicacao (id),
    conta_id INTEGER REFERENCES conta (id)
  );

INSERT INTO
  comentario (publicacao_id, conta_id, texto)
VALUES
  (1, 1, 'foto na agua 1'),
  (1, 2, 'foto na agua 2'),
  (2, 1, 'foto de paisagem 1');

CREATE TABLE
  arquivo (
    id SERIAL PRIMARY KEY,
    publicacao_id INTEGER REFERENCES publicacao (id),
    arquivo TEXT NOT NULL
  );

INSERT INTO
  arquivo (publicacao_id, arquivo)
VALUES
  (1, 'foto_na_agua_2.jpeg'),
  (1, 'foto_na_agua_3.jpeg'),
  (2, 'foto_de_paisagem_2.png'),
  (3, 'dump_trimestral_2.gif');

-- Listar o nome do usuário e o nomes das suas contas
SELECT
  u.nome,
  cnt.nome_usuario
FROM
  usuario u
  JOIN conta cnt ON u.id = cnt.usuario_id
ORDER BY
  u.nome,
  cnt.nome_usuario;

-- Listar as publicações e seus arquivos
SELECT
  p.id,
  p.texto,
  p.arquivo_principal
FROM
  publicacao p
  JOIN conta_publicacao cp ON p.id = cp.publicacao_id
  JOIN conta cnt ON cp.conta_id = cnt.id
  JOIN usuario u ON cnt.usuario_id = u.id
ORDER BY
  p.id,
  cnt.nome_usuario;

-- Listar as publicações e seus comentários 
SELECT
  p.id,
  p.texto,
  p.arquivo_principal,
  cmnt.data_hora,
  cmnt.texto AS comentario
FROM
  publicacao p
  JOIN conta_publicacao cp ON p.id = cp.publicacao_id
  JOIN conta cnt ON cp.conta_id = cnt.id
  JOIN usuario u ON cnt.usuario_id = u.id
  JOIN comentario cmnt ON p.id = cmnt.publicacao_id
ORDER BY
  p.id,
  cmnt.data_hora;

-- Listar somente publicações com comentários
SELECT
  p.id,
  p.texto,
  p.arquivo_principal,
  cmnt.data_hora,
  cmnt.texto AS comentario
FROM
  publicacao p
  JOIN conta_publicacao cp ON p.id = cp.publicacao_id
  JOIN conta cnt ON cp.conta_id = cnt.id
  JOIN usuario u ON cnt.usuario_id = u.id
  JOIN comentario cmnt ON p.id = cmnt.publicacao_id
WHERE
  cmnt.publicacao_id IS NOT NULL
ORDER BY
  p.id,
  cmnt.data_hora;

-- Retornar a quantidade de contas por usuário
SELECT
  u.nome,
  COUNT(cnt.id) AS quantidade_contas
FROM
  usuario u
  JOIN conta cnt ON u.id = cnt.usuario_id
GROUP BY
  u.nome
ORDER BY
  u.nome;

-- Retornar a quantidade de publicações por usuário
SELECT
  u.nome,
  COUNT(p.id) AS quantidade_publicacoes
FROM
  usuario u
  JOIN conta cnt ON u.id = cnt.usuario_id
  JOIN conta_publicacao cp ON cnt.id = cp.conta_id
  JOIN publicacao p ON cp.publicacao_id = p.id
GROUP BY
  u.nome
ORDER BY
  u.nome;

-- Retornar as publicações com mais comentários
SELECT
  p.id,
  p.texto,
  COUNT(cmnt.id) AS quantidade_comentarios
FROM
  publicacao p
  JOIN conta_publicacao cp ON p.id = cp.publicacao_id
  JOIN conta cnt ON cp.conta_id = cnt.id
  JOIN usuario u ON cnt.usuario_id = u.id
  JOIN comentario cmnt ON p.id = cmnt.publicacao_id
GROUP BY
  p.id,
  p.texto
ORDER BY
  quantidade_comentarios DESC;

-- Retornar publicações que não tem comentários
SELECT
  p.id,
  p.texto
FROM
  publicacao p
  LEFT JOIN comentario cmnt ON p.id = cmnt.publicacao_id
WHERE
  cmnt.publicacao_id IS NULL
ORDER BY
  p.id;

-- Retornar somente usuários que possuem uma única conta
SELECT
  u.nome,
  COUNT(cnt.id) AS quantidade_contas
FROM
  usuario u
  JOIN conta cnt ON u.id = cnt.usuario_id
GROUP BY
  u.nome
HAVING
  COUNT(cnt.id) = 1
ORDER BY
  u.nome;

-- Retornar usuários com mais de uma conta sob sua responsabilidade
SELECT
  u.nome,
  COUNT(cnt.id) AS quantidade_contas
FROM
  usuario u
  JOIN conta cnt ON u.id = cnt.usuario_id
GROUP BY
  u.nome
HAVING
  COUNT(cnt.id) > 1
ORDER BY
  u.nome;

-- Retornar publicações sem arquivos adicionais (Sem registros na tabela de arquivo)
SELECT
  p.id,
  p.texto
FROM
  publicacao p
  LEFT JOIN arquivo arq ON p.id = arq.publicacao_id
WHERE
  arq.publicacao_id IS NULL
ORDER BY
  p.id;

-- Retornar somente publicações compartilhadas por mais de uma conta
SELECT
  p.id,
  p.texto
FROM
  publicacao p
  JOIN conta_publicacao cp ON p.id = cp.publicacao_id
  JOIN conta cnt ON cp.conta_id = cnt.id
  JOIN usuario u ON cnt.usuario_id = u.id
GROUP BY
  p.id,
  p.texto
HAVING
  COUNT(cnt.id) > 1
ORDER BY
  p.id;

-- Retornar usuários e suas respectivas contas que não criaram nenhuma publicação
SELECT
  u.nome,
  cnt.nome_usuario
FROM
  usuario u
  JOIN conta cnt ON u.id = cnt.usuario_id
  LEFT JOIN conta_publicacao cp ON cnt.id = cp.conta_id
  LEFT JOIN publicacao p ON cp.publicacao_id = p.id
WHERE
  p.id IS NULL
ORDER BY
  u.nome,
  cnt.nome_usuario;

-- Retornar usuários que possuem só publicações sem comentários
SELECT
  u.nome,
  COUNT(p.id) AS quantidade_publicacoes
FROM
  usuario u
  JOIN conta cnt ON u.id = cnt.usuario_id
  JOIN conta_publicacao cp ON cnt.id = cp.conta_id
  JOIN publicacao p ON cp.publicacao_id = p.id
  LEFT JOIN comentario cmnt ON p.id = cmnt.publicacao_id
GROUP BY
  u.nome
HAVING
  COUNT(cmnt.id) = 0
ORDER BY
  u.nome;

-- Retornar a conta que mais realizou comentários
SELECT
  cnt.id,
  COUNT(cmnt.id) AS quantidade_comentarios
FROM
  conta cnt
  JOIN comentario cmnt ON cmnt.conta_id = cnt.id
GROUP BY
  cnt.id
ORDER BY
  quantidade_comentarios DESC
LIMIT
  1;

-- Retornar o nome do usuário e o nome da conta da última conta criada
SELECT
  u.nome,
  cnt.nome_usuario
FROM
  usuario u
  JOIN conta cnt ON u.id = cnt.usuario_id
WHERE
  cnt.data_hora_criacao = (
    SELECT
      MAX(data_hora_criacao)
    FROM
      conta
  )
ORDER BY
  u.nome,
  cnt.nome_usuario;

-- Retornar usuário(s) que possue(m) a maior quantidade de contas. Retornar pelo menos 1. Nesta questão não obrigatório retornar todos usuários empatados.
SELECT
  u.nome,
  COUNT(cnt.id) AS quantidade_contas
FROM
  usuario u
  JOIN conta cnt ON u.id = cnt.usuario_id
GROUP BY
  u.nome
HAVING
  COUNT(cnt.id) = (
    SELECT
      MAX(quantidade_contas)
    FROM
      (
        SELECT
          COUNT(cnt.id) AS quantidade_contas
        FROM
          usuario u
          JOIN conta cnt ON u.id = cnt.usuario_id
        GROUP BY
          u.nome
      ) AS subquery
  )
ORDER BY
  u.nome;

-- Retornar comentários realizados durante a última semana (últimos 7 dias)
SELECT
  cmnt.id,
  cmnt.data_hora,
  cmnt.texto
FROM
  comentario cmnt
WHERE
  cmnt.data_hora >= NOW() - INTERVAL '7 days'
ORDER BY
  cmnt.data_hora DESC;

-- Retornar as contas do(s) usuário(s) mais velho(s). Retornar todos em caso de empate
SELECT
  u.nome,
  cnt.nome_usuario
FROM
  usuario u
  JOIN conta cnt ON u.id = cnt.usuario_id
WHERE
  u.data_nascimento = (
    SELECT
      MIN(data_nascimento)
    FROM
      usuario
  )
ORDER BY
  u.nome,
  cnt.nome_usuario;

-- Listar nos primeiros resultados usuários sem conta acima dos usuários com conta
SELECT
  u.nome,
  COUNT(cnt.id) AS quantidade_contas
FROM
  usuario u
  LEFT JOIN conta cnt ON u.id = cnt.usuario_id
GROUP BY
  u.nome
ORDER BY
  quantidade_contas DESC;

-- Quantidade total de comentários dado um intervalo de datas
SELECT
  COUNT(cmnt.id) AS quantidade_comentarios
FROM
  comentario cmnt
WHERE
  cmnt.data_hora BETWEEN '2023-01-01' AND '2023-12-31'
GROUP BY
  cmnt.data_hora
ORDER BY
  cmnt.data_hora DESC;

-- Selecione publicações que tenham mais de um arquivo (fora o obrigatório)
SELECT
  p.id,
  p.texto,
  COUNT(arq.id) AS quantidade_arquivos
FROM
  publicacao p
  JOIN arquivo arq ON p.id = arq.publicacao_id
GROUP BY
  p.id,
  p.texto
HAVING
  COUNT(arq.id) > 1
ORDER BY
  quantidade_arquivos DESC;

-- Publicações com maior número de caracteres (coluna texto). Cuidar a questão do empate, ou seja, 2 ou mais publicações terem o texto com o mesma quantidade de caracteres
SELECT
  p.id,
  p.texto,
  LENGTH(p.texto) AS quantidade_caracteres
FROM
  publicacao p
GROUP BY
  p.id,
  p.texto
HAVING
  LENGTH(p.texto) = (
    SELECT
      MAX(LENGTH(texto))
    FROM
      publicacao
  )
ORDER BY
  quantidade_caracteres DESC;

-- Usuário que mais publicou em um dado intervalo de tempo
SELECT
  u.nome,
  COUNT(p.id) AS quantidade_publicacoes
FROM
  usuario u
  JOIN conta cnt ON u.id = cnt.usuario_id
  JOIN conta_publicacao cp ON cnt.id = cp.conta_id
  JOIN publicacao p ON cp.publicacao_id = p.id
WHERE
  p.data_hora BETWEEN '2023-01-01' AND '2023-12-31'
GROUP BY
  u.nome
ORDER BY
  quantidade_publicacoes DESC
LIMIT
  1;

-- Conta que mais publicou
SELECT
  cnt.id,
  COUNT(p.id) AS quantidade_publicacoes
FROM
  conta cnt
  JOIN conta_publicacao cp ON cnt.id = cp.conta_id
  JOIN publicacao p ON cp.publicacao_id = p.id
GROUP BY
  cnt.id
ORDER BY
  quantidade_publicacoes DESC
LIMIT
  1;

-- Conta que mais compartilhou publicações
SELECT
  cnt.id,
  COUNT(cp.publicacao_id) AS quantidade_publicacoes
FROM
  conta cnt
  JOIN conta_publicacao cp ON cnt.id = cp.conta_id
  JOIN publicacao p ON cp.publicacao_id = p.id
GROUP BY
  cnt.id
ORDER BY
  quantidade_publicacoes DESC
LIMIT
  1;

-- Publicação com mais arquivos
SELECT
  p.id,
  COUNT(arq.id) AS quantidade_arquivos
FROM
  publicacao p
  JOIN arquivo arq ON p.id = arq.publicacao_id
GROUP BY
  p.id
ORDER BY
  quantidade_arquivos DESC
LIMIT
  1;

-- Alterar a tabela conta_publicação e adicionar a data e hora em que uma publicação foi compartilhada
ALTER TABLE conta_publicacao
ADD COLUMN data_hora_compartilhamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Usuário que mais realizou comentários
SELECT
  u.nome,
  COUNT(cmnt.id) AS quantidade_comentarios
FROM
  usuario u
  JOIN conta cnt ON u.id = cnt.usuario_id
  JOIN comentario cmnt ON cmnt.conta_id = cnt.id
GROUP BY
  u.nome
ORDER BY
  quantidade_comentarios DESC
LIMIT
  1;

-- Conta que mais realizou comentários
SELECT
  cnt.id,
  COUNT(cmnt.id) AS quantidade_comentarios
FROM
  conta cnt
  JOIN comentario cmnt ON cmnt.conta_id = cnt.id
GROUP BY
  cnt.id
ORDER BY
  quantidade_comentarios DESC
LIMIT
  1;