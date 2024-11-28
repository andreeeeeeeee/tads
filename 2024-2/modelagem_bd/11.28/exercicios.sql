-- Selecione todos os usuários cadastrados na tabela usuario.
SELECT * FROM usuario;

-- Selecione todos os artigos da tabela artigo ordenados pelo título em ordem alfabética.
SELECT * FROM artigo ORDER BY titulo ASC;

-- Selecione todos os usuários que têm o bairro preenchido.
SELECT * FROM usuario WHERE bairro IS NOT NULL;

-- Selecione todos os artigos publicados entre duas datas específicas.
SELECT * FROM artigo WHERE data_hora BETWEEN '2024-11-27' AND '2024-11-29';

-- Selecione todos os usuários que nasceram antes do ano 2000.
SELECT * FROM usuario WHERE data_nascimento >= '2000-01-01';

-- Selecione todos os artigos que pertencem à categoria com ID 1.
SELECT * FROM artigo WHERE categoria_id = 1;

-- Selecione todos os usuários cujos nomes começam com a letra 'I'.
SELECT * FROM usuario WHERE nome LIKE 'I%';

-- Selecione todos os telefones associados ao usuário com ID 1.
SELECT * FROM telefone WHERE usuario_id = 1;

-- Selecione todos os artigos que contêm a palavra 'texto' no campo texto.
SELECT * FROM artigo WHERE texto LIKE '%texto%';

-- Selecione todos os usuários que têm o CEP '12345678'.
SELECT * FROM usuario WHERE cep LIKE '12345678';

-- Atualize o nome do usuário com ID 1 para 'Igor Pereira'.
UPDATE usuario SET nome = 'Igor Pereira' WHERE id =1;

-- Atualize o bairro do usuário com ID 1 para 'Centro'.
UPDATE usuario SET bairro = 'Centro' WHERE id = 1;

-- Atualize o título do artigo com ID 1 para 'Novo Título'.
UPDATE artigo SET titulo = 'Novo Título' WHERE id = 1;

-- Atualize a senha do usuário com email 'igor.pereira@riogrande.ifrs.edu.br' para 'nova_senha'.
UPDATE usuario SET senha = md5('nova_senha') WHERE email = 'igor.pereira@riogrande.ifrs.edu.br';

-- Atualize o número de telefone do telefone com ID 1 para '987654321'.
UPDATE telefone SET nro = '987654321' WHERE id = 1;

-- Delete o usuário com ID 1.
BEGIN;
DELETE FROM artigo WHERE usuario_id = 1;
DELETE FROM usuario WHERE id = 1;
COMMIT;

-- Delete todos os artigos que foram publicados antes de 2024.
DELETE FROM artigo WHERE data_hora < '2024-01-01';

-- Delete todos os comentários associados ao artigo com ID 1.
DELETE FROM comentario WHERE artigo_id = 1;

-- Delete todas as curtidas associadas ao usuário com ID 1.
DELETE FROM curtida WHERE usuario_id = 1;

-- Delete todos os telefones associados ao usuário com ID 1.
DELETE FROM telefone WHERE usuario_id = 1;

-- Adicione uma coluna telefone na tabela usuario.
