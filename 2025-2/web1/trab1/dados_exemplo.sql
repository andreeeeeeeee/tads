INSERT INTO diretores (nome, nota_media, idade, premios) VALUES
('Christopher Nolan', 8.9, 54, 5),
('Steven Spielberg', 8.5, 77, 12),
('Quentin Tarantino', 8.7, 61, 4),
('Martin Scorsese', 8.8, 81, 8),
('James Cameron', 8.4, 69, 3);

INSERT INTO filmes (nome, sinopse, duracao, classificacao) VALUES
('Oppenheimer', 'A história de J. Robert Oppenheimer e a criação da bomba atômica', 180, '14'),
('Inception', 'Um ladrão que invade sonhos recebe a tarefa de plantar uma ideia', 148, '14'),
('Jurassic Park', 'Dinossauros clonados escapam em um parque temático', 127, '12'),
('Pulp Fiction', 'Histórias entrelaçadas do submundo do crime em Los Angeles', 154, '18'),
('Taxi Driver', 'Um motorista de táxi veterano da guerra do Vietnã em Nova York', 114, '18'),
('Avatar', 'Um marine paraplégico enviado para a lua Pandora', 162, '12'),
('Titanic', 'Romance entre passageiros durante o naufrágio do Titanic', 195, '12');

-- Christopher Nolan
INSERT INTO filmes_diretores (filme_id, diretor_id) VALUES (1, 1), (2, 1);
-- Steven Spielberg
INSERT INTO filmes_diretores (filme_id, diretor_id) VALUES (3, 2);
-- Quentin Tarantino
INSERT INTO filmes_diretores (filme_id, diretor_id) VALUES (4, 3);
-- Martin Scorsese
INSERT INTO filmes_diretores (filme_id, diretor_id) VALUES (5, 4);
-- James Cameron
INSERT INTO filmes_diretores (filme_id, diretor_id) VALUES (6, 5), (7, 5);
