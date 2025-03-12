/*
Adicione a coluna data_nascimento na tabela de usuários.
Além disso, coloque uma cláusula CHECK permitindo somente
anos de nascimento >= 1900
 */
ALTER TABLE usuario
ADD COLUMN data_nascimento DATE CHECK (
    extract(
        year
        from
            data_nascimento
    ) >= 1900
);

/*
Retorne os nomes dos usuários e suas datas de nascimento formatadas em dia/mes/ano.
Para testar será preciso inserir ou atualizar as datas de nascimento 
de alguns usuários
*/
UPDATE usuario SET data_nascimento = '2005-03-26' WHERE id = 1;
UPDATE usuario SET data_nascimento = '1987-01-20' WHERE id = 2;

SELECT
    nome,
    to_char (data_nascimento, 'DD/MM/YYYY') as nascimento
FROM
    usuario;

/*
Delete usuários sem nome
 */
DELETE FROM usuario
WHERE
    nome IS NULL;

/*
Torne a coluna nome da tabela usuários obrigatória
 */
ALTER TABLE usuario
ALTER COLUMN nome
SET
    NOT NULL;

/*
Retorne os títulos de todos os álbuns em maiúsculo
 */
SELECT
    upper(titulo) as titulo
FROM
    album;

/*
Retorne somente os títulos dos 2 primeiros álbuns cadastrados
 */
SELECT
    titulo
FROM
    album
LIMIT
    2;

/*
Retorne o nome e o email de todos os usuários separados por ponto-e-vírgula
 */
SELECT
    nome || ';' || email
FROM
    usuario
ORDER BY
    id;

/*
Retorne músicas com duração entre 100 e 200 segundos
 */
SELECT
    m.titulo  as musica, a.titulo as album, m.duracao
FROM
    musica m, album a
WHERE
    m.duracao BETWEEN 100 AND 200 AND m.album_id = a.id;

/*
Retorne músicas que não possuem duração entre 100 e 200 segundos
 */
SELECT
    m.titulo  as musica, a.titulo as album, m.duracao
FROM
    musica m, album a
WHERE
    m.duracao NOT BETWEEN 100 AND 200 AND m.album_id = a.id;

/*
Retorne artistas que possuem nome e nome artístico
 */
SELECT
    *
FROM
    artista
WHERE
    nome IS NOT NULL
    AND nome_artistico IS NOT NULL;

/*
Retorne, preferencialmente, o nome de todos os artistas.
Caso um determinado artista não tenha cadastrado seu nome,
retorne na mesma consulta seu nome artístico.
*/
SELECT
    id,
    coalesce(nome, nome_artistico)
FROM
    artista;

SELECT 
CASE
    WHEN nome_artistico IS NOT NULL THEN nome_artistico
    ELSE artista.nome
END, album.titulo FROM artista INNER JOIN album ON (artista.id = album.artista_id);

/*
Retorne o título dos álbuns lançados em 2023
 */
SELECT
    titulo
FROM
    album
WHERE
    extract(
        year
        from
            data_lancamento
    ) = 2023;

/*
Retorne o nome das playlists que foram criadas hoje
*/
SELECT nome, cast(data_hora AS DATE) data FROM playlist WHERE cast(data_hora AS DATE) = current_date;

--SELECT nome, data_hora::DATE AS data FROM playlist WHERE data_hora::DATE = current_date;

/*
Atualize todos os nomes dos artistas (nome e nome_artistico) para maiúsculo
*/
UPDATE artista SET nome = upper(nome);

/*
Coloque uma verificação para a coluna duracao (tabela musica) para que
toda duração tenha mais de 0 segundos
*/


/*
Adicione uma restrição UNIQUE para a coluna email da tabela usuario
*/
ALTER TABLE usuario ALTER COLUMN email SET UNIQUE;

/*
Retorne somente os artistas que o nome artístico começa com "Leo"
(Ex: Leo Santana, Leonardo e etc.)
*/
SELECT coalesce(nome_artistico,nome) as artista FROM artista WHERE nome_artistico LIKE 'Leo%';

/*
Retorne o título dos álbuns que estão fazendo aniversário neste mês
*/
SELECT titulo, data_lancamento FROM album WHERE extract(month from data_lancamento) = extract(month from current_date);

/*
Retorne o título dos álbuns lançados no segundo semestre do ano passado
(de julho de 2022 a dezembro de 2022)
*/

/*
Retorne o título dos álbuns lançados nos últimos 30 dias
https://www.postgresql.org/docs/current/functions-datetime.html
*/
SELECT titulo FROM album WHERE data_lancamento >= current_date-cast('30 day' as interval); 

/*
Retorne o título e o dia de lançamento (por extenso) de todos os álbuns
*/
SELECT titulo, case extract(dow from data_lancamento)
		when 0 then 'domingo'
		when 1 then 'segunda'
		when 2 then 'terca'
		when 3 then 'quarta'
		when 4 then 'quinta'
		when 5 then 'sexta'
		when 6 then 'sabado' 
        end as dia_lancamento FROM album;

/*
Retorne o título e o mês de lançamento (por extenso) de todos os álbuns
*/
SELECT titulo, case extract(month from data_lancamento)
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
	end as mes FROM album;

/*
Retorne pelo menos um dos álbuns mais antigos
*/
SELECT * FROM album ORDER BY data_lancamento ASC;

/*
Retorne pelo menos um dos álbuns mais recentes
*/
SELECT * FROM album ORDER BY data_lancamento DESC;

/*
Liste os títulos das músicas de todos os álbuns de um determinado artista
*/

/*
Liste os títulos das músicas de um álbum de um determinado artista
*/
/*
Liste somente os nomes de usuários que possuem alguma playlist (cuidado! com a repetição)
*/
/*
Liste artistas que ainda não possuem álbuns cadastrados
*/
/*
Liste usuários que ainda não possuem playlists cadastradas
*/
/*
Retorne a quantidade de álbuns por artista
*/
/*
Retorne a quantidade de músicas por artista
*/
/*
Retorne o título das músicas de uma playlist de um determinado usuário
*/
/*
Retorne a quantidade de playlist de um determinado usuário
*/
/*
Retone a quantidade de músicas por artista (de artistas que possuem pelo menos 2 músicas)
*/
/*
Retorne os títulos de todos os álbuns lançados no mesmo ano em que o álbum mais antigo foi lançado
*/
/*
Retorne os títulos de todos os álbuns lançados no mesmo ano em que o álbum mais novo foi lançado
*/
/*
Retorne na mesma consulta os nomes de todos os artistas e de todos os usuários. Caso um determinado artista não tenha cadastrado seu nome, retorne seu nome artístico
*/
/*
Retorne nomes das playlists com e sem músicas
*/
/*
Retorne a média da quantidade de músicas de todas as playlists
*/
/*
Retorne somente playlists que possuem quantidade de músicas maior ou igual a média
*/
