INSERT INTO direcao (nome) VALUES
('Christopher Nolan'),
('Steven Spielberg'),
('Quentin Tarantino'),
('Martin Scorsese'),
('James Cameron');

INSERT INTO genero (nome) VALUES
('Ação'),
('Drama'),
('Ficção Científica'),
('Terror'),
('Comédia'),
('Romance');

INSERT INTO filme (titulo, duracao, classificacao_etaria, sinopse) VALUES
('Inception', 148, '14', 'Um ladrão que rouba segredos corporativos através do uso de tecnologia de compartilhamento de sonhos.'),
('Jurassic Park', 127, '12', 'Um parque temático com dinossauros clonados sai do controle.'),
('Pulp Fiction', 154, '18', 'Várias histórias interconectadas envolvendo crime e redenção em Los Angeles.'),
('The Wolf of Wall Street', 180, '18', 'A ascensão e queda de um corretor da bolsa de valores.'),
('Avatar', 162, '12', 'Um ex-fuzileiro naval é enviado para Pandora em uma missão corporativa.');

INSERT INTO filme_direcao (filme_id, direcao_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

INSERT INTO filme_genero (filme_id, genero_id) VALUES
(1, 1), (1, 3),
(2, 1), (2, 3),
(3, 2), (3, 1),
(4, 2),
(5, 1), (5, 3);

INSERT INTO sala (nome, ocupacao) VALUES
('Sala 1', 100),
('Sala 2', 80),
('Sala 3', 120),
('Sala 4', 60);

INSERT INTO poltrona (sala_id, fileira, posicao, tipo) VALUES
(1, 'A', 1, 'simples'), (1, 'A', 2, 'simples'), (1, 'A', 3, 'simples'), (1, 'A', 4, 'simples'), (1, 'A', 5, 'simples'),
(1, 'A', 6, 'simples'), (1, 'A', 7, 'simples'), (1, 'A', 8, 'simples'), (1, 'A', 9, 'simples'), (1, 'A', 10, 'simples'),
(1, 'B', 1, 'simples'), (1, 'B', 2, 'simples'), (1, 'B', 3, 'simples'), (1, 'B', 4, 'simples'), (1, 'B', 5, 'simples'),
(1, 'B', 6, 'simples'), (1, 'B', 7, 'simples'), (1, 'B', 8, 'simples'), (1, 'B', 9, 'simples'), (1, 'B', 10, 'simples'),
(1, 'C', 1, 'luxo'), (1, 'C', 2, 'luxo'), (1, 'C', 3, 'luxo'), (1, 'C', 4, 'luxo'), (1, 'C', 5, 'luxo'),
(1, 'C', 6, 'luxo'), (1, 'C', 7, 'luxo'), (1, 'C', 8, 'luxo'), (1, 'C', 9, 'luxo'), (1, 'C', 10, 'luxo'),
(1, 'D', 1, 'luxo'), (1, 'D', 2, 'luxo'), (1, 'D', 3, 'luxo'), (1, 'D', 4, 'luxo'), (1, 'D', 5, 'luxo'),
(1, 'D', 6, 'luxo'), (1, 'D', 7, 'luxo'), (1, 'D', 8, 'luxo'), (1, 'D', 9, 'luxo'), (1, 'D', 10, 'luxo'),
(1, 'E', 1, 'simples'), (1, 'E', 2, 'simples'), (1, 'E', 3, 'simples'), (1, 'E', 4, 'simples'), (1, 'E', 5, 'simples'),
(1, 'E', 6, 'simples'), (1, 'E', 7, 'simples'), (1, 'E', 8, 'simples'), (1, 'E', 9, 'simples'), (1, 'E', 10, 'simples'),
(1, 'F', 1, 'simples'), (1, 'F', 2, 'simples'), (1, 'F', 3, 'simples'), (1, 'F', 4, 'simples'), (1, 'F', 5, 'simples'),
(1, 'F', 6, 'simples'), (1, 'F', 7, 'simples'), (1, 'F', 8, 'simples'), (1, 'F', 9, 'simples'), (1, 'F', 10, 'simples'),
(1, 'G', 1, 'luxo'), (1, 'G', 2, 'luxo'), (1, 'G', 3, 'luxo'), (1, 'G', 4, 'luxo'), (1, 'G', 5, 'luxo'),
(1, 'G', 6, 'luxo'), (1, 'G', 7, 'luxo'), (1, 'G', 8, 'luxo'), (1, 'G', 9, 'luxo'), (1, 'G', 10, 'luxo'),
(1, 'H', 1, 'luxo'), (1, 'H', 2, 'luxo'), (1, 'H', 3, 'luxo'), (1, 'H', 4, 'luxo'), (1, 'H', 5, 'luxo'),
(1, 'H', 6, 'luxo'), (1, 'H', 7, 'luxo'), (1, 'H', 8, 'luxo'), (1, 'H', 9, 'luxo'), (1, 'H', 10, 'luxo'),
(1, 'I', 1, 'simples'), (1, 'I', 2, 'simples'), (1, 'I', 3, 'simples'), (1, 'I', 4, 'simples'), (1, 'I', 5, 'simples'),
(1, 'I', 6, 'simples'), (1, 'I', 7, 'simples'), (1, 'I', 8, 'simples'), (1, 'I', 9, 'simples'), (1, 'I', 10, 'simples'),
(1, 'J', 1, 'simples'), (1, 'J', 2, 'simples'), (1, 'J', 3, 'simples'), (1, 'J', 4, 'simples'), (1, 'J', 5, 'simples'),
(1, 'J', 6, 'simples'), (1, 'J', 7, 'simples'), (1, 'J', 8, 'simples'), (1, 'J', 9, 'simples'), (1, 'J', 10, 'simples');

INSERT INTO poltrona (sala_id, fileira, posicao, tipo) VALUES
(2, 'A', 1, 'simples'), (2, 'A', 2, 'simples'), (2, 'A', 3, 'simples'), (2, 'A', 4, 'simples'), (2, 'A', 5, 'simples'),
(2, 'A', 6, 'simples'), (2, 'A', 7, 'simples'), (2, 'A', 8, 'simples'), (2, 'A', 9, 'simples'), (2, 'A', 10, 'simples'),
(2, 'B', 1, 'simples'), (2, 'B', 2, 'simples'), (2, 'B', 3, 'simples'), (2, 'B', 4, 'simples'), (2, 'B', 5, 'simples'),
(2, 'B', 6, 'simples'), (2, 'B', 7, 'simples'), (2, 'B', 8, 'simples'), (2, 'B', 9, 'simples'), (2, 'B', 10, 'simples'),
(2, 'C', 1, 'luxo'), (2, 'C', 2, 'luxo'), (2, 'C', 3, 'luxo'), (2, 'C', 4, 'luxo'), (2, 'C', 5, 'luxo'),
(2, 'C', 6, 'luxo'), (2, 'C', 7, 'luxo'), (2, 'C', 8, 'luxo'), (2, 'C', 9, 'luxo'), (2, 'C', 10, 'luxo'),
(2, 'D', 1, 'luxo'), (2, 'D', 2, 'luxo'), (2, 'D', 3, 'luxo'), (2, 'D', 4, 'luxo'), (2, 'D', 5, 'luxo'),
(2, 'D', 6, 'luxo'), (2, 'D', 7, 'luxo'), (2, 'D', 8, 'luxo'), (2, 'D', 9, 'luxo'), (2, 'D', 10, 'luxo'),
(2, 'E', 1, 'simples'), (2, 'E', 2, 'simples'), (2, 'E', 3, 'simples'), (2, 'E', 4, 'simples'), (2, 'E', 5, 'simples'),
(2, 'E', 6, 'simples'), (2, 'E', 7, 'simples'), (2, 'E', 8, 'simples'), (2, 'E', 9, 'simples'), (2, 'E', 10, 'simples'),
(2, 'F', 1, 'simples'), (2, 'F', 2, 'simples'), (2, 'F', 3, 'simples'), (2, 'F', 4, 'simples'), (2, 'F', 5, 'simples'),
(2, 'F', 6, 'simples'), (2, 'F', 7, 'simples'), (2, 'F', 8, 'simples'), (2, 'F', 9, 'simples'), (2, 'F', 10, 'simples'),
(2, 'G', 1, 'luxo'), (2, 'G', 2, 'luxo'), (2, 'G', 3, 'luxo'), (2, 'G', 4, 'luxo'), (2, 'G', 5, 'luxo'),
(2, 'G', 6, 'luxo'), (2, 'G', 7, 'luxo'), (2, 'G', 8, 'luxo'), (2, 'G', 9, 'luxo'), (2, 'G', 10, 'luxo'),
(2, 'H', 1, 'luxo'), (2, 'H', 2, 'luxo'), (2, 'H', 3, 'luxo'), (2, 'H', 4, 'luxo'), (2, 'H', 5, 'luxo'),
(2, 'H', 6, 'luxo'), (2, 'H', 7, 'luxo'), (2, 'H', 8, 'luxo'), (2, 'H', 9, 'luxo'), (2, 'H', 10, 'luxo');

INSERT INTO poltrona (sala_id, fileira, posicao, tipo) VALUES
(3, 'A', 1, 'simples'), (3, 'A', 2, 'simples'), (3, 'A', 3, 'simples'), (3, 'A', 4, 'simples'), (3, 'A', 5, 'simples'),
(3, 'A', 6, 'simples'), (3, 'A', 7, 'simples'), (3, 'A', 8, 'simples'), (3, 'A', 9, 'simples'), (3, 'A', 10, 'simples'),
(3, 'B', 1, 'simples'), (3, 'B', 2, 'simples'), (3, 'B', 3, 'simples'), (3, 'B', 4, 'simples'), (3, 'B', 5, 'simples'),
(3, 'B', 6, 'simples'), (3, 'B', 7, 'simples'), (3, 'B', 8, 'simples'), (3, 'B', 9, 'simples'), (3, 'B', 10, 'simples'),
(3, 'C', 1, 'luxo'), (3, 'C', 2, 'luxo'), (3, 'C', 3, 'luxo'), (3, 'C', 4, 'luxo'), (3, 'C', 5, 'luxo'),
(3, 'C', 6, 'luxo'), (3, 'C', 7, 'luxo'), (3, 'C', 8, 'luxo'), (3, 'C', 9, 'luxo'), (3, 'C', 10, 'luxo'),
(3, 'D', 1, 'luxo'), (3, 'D', 2, 'luxo'), (3, 'D', 3, 'luxo'), (3, 'D', 4, 'luxo'), (3, 'D', 5, 'luxo'),
(3, 'D', 6, 'luxo'), (3, 'D', 7, 'luxo'), (3, 'D', 8, 'luxo'), (3, 'D', 9, 'luxo'), (3, 'D', 10, 'luxo'),
(3, 'E', 1, 'simples'), (3, 'E', 2, 'simples'), (3, 'E', 3, 'simples'), (3, 'E', 4, 'simples'), (3, 'E', 5, 'simples'),
(3, 'E', 6, 'simples'), (3, 'E', 7, 'simples'), (3, 'E', 8, 'simples'), (3, 'E', 9, 'simples'), (3, 'E', 10, 'simples'),
(3, 'F', 1, 'simples'), (3, 'F', 2, 'simples'), (3, 'F', 3, 'simples'), (3, 'F', 4, 'simples'), (3, 'F', 5, 'simples'),
(3, 'F', 6, 'simples'), (3, 'F', 7, 'simples'), (3, 'F', 8, 'simples'), (3, 'F', 9, 'simples'), (3, 'F', 10, 'simples'),
(3, 'G', 1, 'luxo'), (3, 'G', 2, 'luxo'), (3, 'G', 3, 'luxo'), (3, 'G', 4, 'luxo'), (3, 'G', 5, 'luxo'),
(3, 'G', 6, 'luxo'), (3, 'G', 7, 'luxo'), (3, 'G', 8, 'luxo'), (3, 'G', 9, 'luxo'), (3, 'G', 10, 'luxo'),
(3, 'H', 1, 'luxo'), (3, 'H', 2, 'luxo'), (3, 'H', 3, 'luxo'), (3, 'H', 4, 'luxo'), (3, 'H', 5, 'luxo'),
(3, 'H', 6, 'luxo'), (3, 'H', 7, 'luxo'), (3, 'H', 8, 'luxo'), (3, 'H', 9, 'luxo'), (3, 'H', 10, 'luxo'),
(3, 'I', 1, 'simples'), (3, 'I', 2, 'simples'), (3, 'I', 3, 'simples'), (3, 'I', 4, 'simples'), (3, 'I', 5, 'simples'),
(3, 'I', 6, 'simples'), (3, 'I', 7, 'simples'), (3, 'I', 8, 'simples'), (3, 'I', 9, 'simples'), (3, 'I', 10, 'simples'),
(3, 'J', 1, 'simples'), (3, 'J', 2, 'simples'), (3, 'J', 3, 'simples'), (3, 'J', 4, 'simples'), (3, 'J', 5, 'simples'),
(3, 'J', 6, 'simples'), (3, 'J', 7, 'simples'), (3, 'J', 8, 'simples'), (3, 'J', 9, 'simples'), (3, 'J', 10, 'simples'),
(3, 'K', 1, 'luxo'), (3, 'K', 2, 'luxo'), (3, 'K', 3, 'luxo'), (3, 'K', 4, 'luxo'), (3, 'K', 5, 'luxo'),
(3, 'K', 6, 'luxo'), (3, 'K', 7, 'luxo'), (3, 'K', 8, 'luxo'), (3, 'K', 9, 'luxo'), (3, 'K', 10, 'luxo'),
(3, 'L', 1, 'luxo'), (3, 'L', 2, 'luxo'), (3, 'L', 3, 'luxo'), (3, 'L', 4, 'luxo'), (3, 'L', 5, 'luxo'),
(3, 'L', 6, 'luxo'), (3, 'L', 7, 'luxo'), (3, 'L', 8, 'luxo'), (3, 'L', 9, 'luxo'), (3, 'L', 10, 'luxo');

INSERT INTO poltrona (sala_id, fileira, posicao, tipo) VALUES
(4, 'A', 1, 'simples'), (4, 'A', 2, 'simples'), (4, 'A', 3, 'simples'), (4, 'A', 4, 'simples'), (4, 'A', 5, 'simples'),
(4, 'A', 6, 'simples'), (4, 'A', 7, 'simples'), (4, 'A', 8, 'simples'), (4, 'A', 9, 'simples'), (4, 'A', 10, 'simples'),
(4, 'B', 1, 'simples'), (4, 'B', 2, 'simples'), (4, 'B', 3, 'simples'), (4, 'B', 4, 'simples'), (4, 'B', 5, 'simples'),
(4, 'B', 6, 'simples'), (4, 'B', 7, 'simples'), (4, 'B', 8, 'simples'), (4, 'B', 9, 'simples'), (4, 'B', 10, 'simples'),
(4, 'C', 1, 'luxo'), (4, 'C', 2, 'luxo'), (4, 'C', 3, 'luxo'), (4, 'C', 4, 'luxo'), (4, 'C', 5, 'luxo'),
(4, 'C', 6, 'luxo'), (4, 'C', 7, 'luxo'), (4, 'C', 8, 'luxo'), (4, 'C', 9, 'luxo'), (4, 'C', 10, 'luxo'),
(4, 'D', 1, 'luxo'), (4, 'D', 2, 'luxo'), (4, 'D', 3, 'luxo'), (4, 'D', 4, 'luxo'), (4, 'D', 5, 'luxo'),
(4, 'D', 6, 'luxo'), (4, 'D', 7, 'luxo'), (4, 'D', 8, 'luxo'), (4, 'D', 9, 'luxo'), (4, 'D', 10, 'luxo'),
(4, 'E', 1, 'simples'), (4, 'E', 2, 'simples'), (4, 'E', 3, 'simples'), (4, 'E', 4, 'simples'), (4, 'E', 5, 'simples'),
(4, 'E', 6, 'simples'), (4, 'E', 7, 'simples'), (4, 'E', 8, 'simples'), (4, 'E', 9, 'simples'), (4, 'E', 10, 'simples'),
(4, 'F', 1, 'simples'), (4, 'F', 2, 'simples'), (4, 'F', 3, 'simples'), (4, 'F', 4, 'simples'), (4, 'F', 5, 'simples'),
(4, 'F', 6, 'simples'), (4, 'F', 7, 'simples'), (4, 'F', 8, 'simples'), (4, 'F', 9, 'simples'), (4, 'F', 10, 'simples');

INSERT INTO sessao (filme_id, sala_id, data, hora_inicio, hora_fim) VALUES
(1, 1, '2025-12-01', '14:00', '16:28'),
(2, 2, '2025-12-01', '16:00', '18:07'),
(3, 3, '2025-12-01', '18:00', '20:34'),
(4, 4, '2025-12-01', '20:00', '23:00'),
(5, 1, '2025-12-02', '14:00', '16:42'),
(1, 2, '2025-12-02', '16:00', '18:28'),
(2, 3, '2025-12-02', '18:00', '20:07'),
(3, 4, '2025-12-02', '20:00', '22:34');

INSERT INTO preco (preco_base) VALUES
(25.00),
(35.00);

INSERT INTO cliente (cpf, nome, status) VALUES
('12345678901', 'João Silva', true),
('23456789012', 'Maria Oliveira', true),
('34567890123', 'Pedro Santos', true),
('45678901234', 'Ana Costa', true),
('56789012345', 'Carlos Pereira', true),
('67890123456', 'Fernanda Lima', true),
('78901234567', 'Roberto Alves', true),
('89012345678', 'Juliana Ferreira', true),
('90123456789', 'Lucas Rodrigues', true),
('01234567890', 'Patrícia Gomes', true);

INSERT INTO ingresso (cpf, sessao_id, poltrona_id, valor, preco_id, data_venda) VALUES
('12345678901', 4, 1, 25.00, 1, '2025-11-30 10:00:00'),
('23456789012', 4, 2, 25.00, 1, '2025-11-30 10:05:00'),
('34567890123', 4, 11, 35.00, 2, '2025-11-30 10:10:00'),
('45678901234', 5, 101, 25.00, 1, '2025-11-30 11:00:00'),
('56789012345', 5, 102, 25.00, 1, '2025-11-30 11:05:00'),
(NULL, 6, 201, 0.00, NULL, NULL),
(NULL, 6, 202, 0.00, NULL, NULL),
('67890123456', 7, 301, 35.00, 2, '2025-11-30 12:00:00'),
('78901234567', 7, 302, 35.00, 2, '2025-11-30 12:05:00'),
('89012345678', 8, 401, 25.00, 1, '2025-11-30 13:00:00'),
('90123456789', 8, 402, 25.00, 1, '2025-11-30 13:05:00');
