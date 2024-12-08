-- Parte 3: Consultas Básicas de SQL


-- Inserção de Dados:

-- Insira pelo menos 3 registros na tabela Visitantes.
INSERT INTO visitante (nome, data_nascimento, email) VALUES
('João', '1990-01-01', 'joao@gmail.com'),
('Maria', '1985-02-15', 'maria@gmail.com'),
('Alana', '2003-04-23', 'alana@gmail.com'),
('André', '2005-03-26', 'andre@gmail.com');
-- Insira pelo menos 3 registros na tabela Atrações.
INSERT INTO atracao (nome, tipo, capacidade) VALUES
('Montanha Russa', 'Radical', 50),
('Carrossel', 'Infantil', 30),
('Roda Gigante', 'Família', 40);
-- Insira pelo menos 2 registros na tabela Ingressos.
INSERT INTO ingresso (id_visitante, id_atracao, data_visita) VALUES
(3, 1, '2024-05-30'),
(4, 1, '2024-05-30'),
(3, 3, '2024-11-03'),
(4, 3, '2024-11-03'),
(1, 2, '2024-07-15'),
(2, 1, '2024-01-01'),
(2, 2, '2024-06-30'),
(1, 1, '2024-10-01'),
(2, 2, '2024-02-15');
-- Insira pelo menos 2 registros na tabela Funcionários.
INSERT INTO funcionario (id_atracao, nome, salario, cargo) VALUES
(1, 'José', 3500.00, 'Operador'),
(3, 'Carlos', 2500.00, 'Recepcionista'),
(2, 'Ana', 4000.00, 'Gerente');


-- Consultas Simples:

-- Selecione todos os visitantes cadastrados.
SELECT * FROM visitante;
-- Selecione todas as atrações disponíveis.
SELECT * FROM atracao;


-- Consultas com Filtros (WHERE):

-- Liste todos os visitantes que visitaram uma atração específica.
SELECT a.id_visitante, b.nome FROM ingresso a, visitante b WHERE a.id_atracao = 3 AND a.id_visitante = b.id_visitante;
-- Liste todos os funcionários que têm um salário maior que 3000.
SELECT * FROM funcionario WHERE salario > 3000;


-- Consultas com Ordenação (ORDER BY):

-- Liste todos os visitantes ordenados pelo nome.
SELECT * FROM visitante ORDER BY nome;
-- Liste todas as atrações ordenadas pela capacidade em ordem decrescente.
SELECT * FROM atracao ORDER BY capacidade DESC;


-- Consultas com Funções Básicas:

-- Calcule a média dos salários dos funcionários.
SELECT AVG(salario) FROM funcionario;
-- Encontre a data mais recente de visita registrada.
SELECT MAX(data_visita) FROM ingresso;


-- Consultas com Manipulação de Data e Hora:

-- Liste todos os visitantes que nasceram antes do ano 2000.
SELECT * FROM visitante WHERE data_nascimento < '2000-01-01';
-- Calcule a idade de cada visitante com base na data de nascimento.
SELECT nome, data_nascimento, AGE(data_nascimento) AS idade FROM visitante;