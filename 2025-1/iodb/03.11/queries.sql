/*
 Adicione a coluna data_nascimento na tabela de usuários.
 Além disso, coloque uma cláusula CHECK permitindo somente
 anos de nascimento >= 1900
 */
ALTER TABLE usuario
ADD COLUMN data_nascimento DATE CHECK (
    extract(
      year
      from data_nascimento
    ) >= 1900
  );
/*
 Retorne os nomes dos usuários e suas datas de nascimento formatadas em dia/mes/ano.
 Para testar será preciso inserir ou atualizar as datas de nascimento 
 de alguns usuários
 */
UPDATE usuario
SET data_nascimento = '2005-03-26'
WHERE id = 1;
UPDATE usuario
SET data_nascimento = '1987-01-20'
WHERE id = 2;
SELECT nome,
  to_char (data_nascimento, 'DD/MM/YYYY') as nascimento
FROM usuario;
/*
 Delete usuários sem nome
 */
DELETE FROM usuario
WHERE nome IS NULL;
/*
 Torne a coluna nome da tabela usuários obrigatória
 */
ALTER TABLE usuario
ALTER COLUMN nome
SET NOT NULL;
/*
 Retorne os títulos de todos os álbuns em maiúsculo
 */
SELECT upper(titulo) as titulo
FROM album;
/*
 Retorne somente os títulos dos 2 primeiros álbuns cadastrados
 */
SELECT titulo
FROM album
LIMIT 2;
/*
 Retorne o nome e o email de todos os usuários separados por ponto-e-vírgula
 */
SELECT nome || ';' || email
FROM usuario
ORDER BY id;
/*
 Retorne músicas com duração entre 100 e 200 segundos
 */
SELECT m.titulo as musica,
  a.titulo as album,
  m.duracao
FROM musica m,
  album a
WHERE m.duracao BETWEEN 100 AND 200
  AND m.album_id = a.id;
/*
 Retorne músicas que não possuem duração entre 100 e 200 segundos
 */
SELECT m.titulo as musica,
  a.titulo as album,
  m.duracao
FROM musica m,
  album a
WHERE m.duracao NOT BETWEEN 100 AND 200
  AND m.album_id = a.id;
/*
 Retorne artistas que possuem nome e nome artístico
 */
SELECT *
FROM artista
WHERE nome IS NOT NULL
  AND nome_artistico IS NOT NULL;
/*
 Retorne, preferencialmente, o nome de todos os artistas.
 Caso um determinado artista não tenha cadastrado seu nome,
 retorne na mesma consulta seu nome artístico.
 */
SELECT id,
  coalesce(nome, nome_artistico)
FROM artista;
SELECT CASE
    WHEN nome_artistico IS NOT NULL THEN nome_artistico
    ELSE artista.nome
  END,
  album.titulo
FROM artista
  INNER JOIN album ON (artista.id = album.artista_id);
/*
 Retorne o título dos álbuns lançados em 2023
 */
SELECT titulo
FROM album
WHERE extract(
    year
    from data_lancamento
  ) = 2023;
/*
 Retorne o nome das playlists que foram criadas hoje
 */
SELECT nome,
  cast(data_hora AS DATE) data
FROM playlist
WHERE cast(data_hora AS DATE) = current_date;
--SELECT nome, data_hora::DATE AS data FROM playlist WHERE data_hora::DATE = current_date;
/*
 Atualize todos os nomes dos artistas (nome e nome_artistico) para maiúsculo
 */
UPDATE artista
SET nome = upper(nome);
/*
 Coloque uma verificação para a coluna duracao (tabela musica) para que
 toda duração tenha mais de 0 segundos
 */
/*
 Adicione uma restrição UNIQUE para a coluna email da tabela usuario
 */
ALTER TABLE usuario
ALTER COLUMN email
SET UNIQUE;
/*
 Retorne somente os artistas que o nome artístico começa com "Leo"
 (Ex: Leo Santana, Leonardo e etc.)
 */
SELECT coalesce(nome_artistico, nome) as artista
FROM artista
WHERE nome_artistico LIKE 'Leo%';
/*
 Retorne o título dos álbuns que estão fazendo aniversário neste mês
 */
SELECT titulo,
  data_lancamento
FROM album
WHERE extract(
    month
    from data_lancamento
  ) = extract(
    month
    from current_date
  );
/*
 Retorne o título dos álbuns lançados no segundo semestre do ano passado
 (de julho de 2022 a dezembro de 2022)
 */
SELECT titulo
FROM album
WHERE data_lancamento BETWEEN '2022-07-01' AND '2022-12-31';
/*
 Retorne o título dos álbuns lançados nos últimos 30 dias
 https://www.postgresql.org/docs/current/functions-datetime.html
 */
SELECT titulo
FROM album
WHERE data_lancamento >= current_date - cast('30 day' as interval);
/*
 Retorne o título e o dia de lançamento (por extenso) de todos os álbuns
 */
SELECT titulo,
  case
    extract(
      dow
      from data_lancamento
    )
    when 0 then 'domingo'
    when 1 then 'segunda'
    when 2 then 'terca'
    when 3 then 'quarta'
    when 4 then 'quinta'
    when 5 then 'sexta'
    when 6 then 'sabado'
  end as dia_lancamento
FROM album;
/*
 Retorne o título e o mês de lançamento (por extenso) de todos os álbuns
 */
SELECT titulo,
  case
    extract(
      month
      from data_lancamento
    )
    when 1 then 'janeiro'
    when 2 then 'fevereiro'
    when 3 then 'marco'
    when 4 then 'abril'
    when 5 then 'maio'
    when 6 then 'junho'
    when 7 then 'julho'
    when 8 then 'agosto'
    when 9 then 'setembro'
    when 10 then 'outubro'
    when 11 then 'novembro'
    when 12 then 'dezembro'
  end as mes
FROM album;
/*
 Retorne pelo menos um dos álbuns mais antigos
 */
SELECT *
FROM album
ORDER BY data_lancamento ASC;
/*
 Retorne pelo menos um dos álbuns mais recentes
 */
SELECT *
FROM album
ORDER BY data_lancamento DESC;
/*
 Liste os títulos das músicas de todos os álbuns de um determinado artista
 */
SELECT m.titulo as musica,
  a.titulo as album
FROM musica m
  INNER JOIN album a ON m.album_id = a.id
  INNER JOIN artista ar ON a.artista_id = ar.id
WHERE ar.nome = 'Nome do Artista';
/*
 Liste os títulos das músicas de um álbum de um determinado artista
 */
SELECT m.titulo as musica,
  a.titulo as album
FROM musica m
  INNER JOIN album a ON m.album_id = a.id
  INNER JOIN artista ar ON a.artista_id = ar.id
WHERE ar.nome = 'Nome do Artista'
  AND a.titulo = 'Título do Álbum';
/*
 Liste somente os nomes de usuários que possuem alguma playlist (cuidado! com a repetição)
 */
SELECT u.nome
FROM usuario u
  INNER JOIN playlist p ON u.id = p.usuario_id;
/*
 Liste artistas que ainda não possuem álbuns cadastrados
 */
SELECT *
FROM artista
WHERE id NOT IN (
    SELECT artista_id
    FROM album
  );
/*
 Liste usuários que ainda não possuem playlists cadastradas
 */
SELECT *
FROM usuario
WHERE id NOT IN (
    SELECT usuario_id
    FROM playlist
  );
/*
 Retorne a quantidade de álbuns por artista
 */
SELECT coalesce(ar.nome_artistico, ar.nome) as artista,
  count(a.id) as qtd_albuns
FROM artista ar
  INNER JOIN album a ON ar.id = a.artista_id
GROUP BY ar.nome;
/*
 Retorne a quantidade de músicas por artista
 */
SELECT coalesce(ar.nome_artistico, ar.nome) as artista,
  count(m.id) as qtd_musicas
FROM artista ar
  INNER JOIN album a ON ar.id = a.artista_id
  INNER JOIN musica m ON a.id = m.album_id
GROUP BY ar.nome;
/*
 Retorne o título das músicas de uma playlist de um determinado usuário
 */
SELECT m.titulo as musica
FROM musica m
  INNER JOIN playlist_musica pm ON m.id = pm.musica_id
  INNER JOIN playlist p ON pm.playlist_id = p.id
  INNER JOIN usuario u ON p.usuario_id = u.id
WHERE u.nome = 'Nome do Usuário'
  AND p.nome = 'Nome da Playlist';
/*
 Retorne a quantidade de playlist de um determinado usuário
 */
SELECT u.nome,
  count(p.id) as qtd_playlist
FROM usuario u
  INNER JOIN playlist p ON u.id = p.usuario_id
GROUP BY u.nome;
/*
 Retone a quantidade de músicas por artista (de artistas que possuem pelo menos 2 músicas)
 */
SELECT coalesce(ar.nome_artistico, ar.nome) as artista,
  count(m.id) as qtd_musicas
FROM artista ar
  INNER JOIN album a ON ar.id = a.artista_id
  INNER JOIN musica m ON a.id = m.album_id
GROUP BY ar.nome
HAVING count(m.id) >= 2;
/*
 Retorne os títulos de todos os álbuns lançados no mesmo ano em que o álbum mais antigo foi lançado
 */
SELECT titulo
FROM album
WHERE extract(
    year
    from data_lancamento
  ) = (
    SELECT extract(
        year
        from min(data_lancamento)
      )
    FROM album
  );
/*
 Retorne os títulos de todos os álbuns lançados no mesmo ano em que o álbum mais novo foi lançado
 */
SELECT titulo
FROM album
WHERE extract(
    year
    from data_lancamento
  ) = (
    SELECT extract(
        year
        from max(data_lancamento)
      )
    FROM album
  );
/*
 Retorne na mesma consulta os nomes de todos os artistas e de todos os usuários. Caso um determinado artista não tenha cadastrado seu nome, retorne seu nome artístico
 */
SELECT coalesce(nome, nome_artistico) as nome
FROM artista
UNION
SELECT nome
FROM usuario;
/*
 Retorne nomes das playlists com e sem músicas
 */
SELECT p.nome,
  count(pm.musica_id) as qtd_musicas
FROM playlist p
  LEFT JOIN playlist_musica pm ON p.id = pm.playlist_id
GROUP BY p.nome;
/*
 Retorne a média da quantidade de músicas de todas as playlists
 */
SELECT avg(count(pm.musica_id))
FROM playlist p
  LEFT JOIN playlist_musica pm ON p.id = pm.playlist_id
GROUP BY p.id;
/*
 Retorne somente playlists que possuem quantidade de músicas maior ou igual a média
 */
SELECT p.nome,
  count(pm.musica_id) as qtd_musicas
FROM playlist p
  LEFT JOIN playlist_musica pm ON p.id = pm.playlist_id
GROUP BY p.nome
HAVING count(pm.musica_id) >= (
    SELECT avg(count(pm.musica_id))
    FROM playlist p
      LEFT JOIN playlist_musica pm ON p.id = pm.playlist_id
    GROUP BY p.id
  );