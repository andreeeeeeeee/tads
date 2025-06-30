-- Criar o banco de dados
DROP DATABASE IF EXISTS edulivre;
CREATE DATABASE edulivre;

-- Conectar ao banco de dados
\c edulivre;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criar tabelas
CREATE TABLE usuario (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL,
    perfil VARCHAR(20) NOT NULL CHECK (perfil IN ('aluno', 'professor', 'admin'))
);

CREATE TABLE curso (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    titulo VARCHAR(200) UNIQUE NOT NULL,
    descricao TEXT NOT NULL,
    data_criacao DATE DEFAULT CURRENT_DATE,
    avaliacao JSONB
);

CREATE TABLE matricula (
    id SERIAL PRIMARY KEY,
    usuario_id UUID NOT NULL,
    curso_id UUID NOT NULL,
    data_matricula DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (usuario_id) REFERENCES Usuario (id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES Curso (id) ON DELETE CASCADE
);

CREATE TABLE conteudo (
    id SERIAL PRIMARY KEY,
    curso_id UUID NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('video', 'pdf', 'imagem', 'audio', 'quiz', 'slide')),
    arquivo BYTEA,
    FOREIGN KEY (curso_id) REFERENCES Curso (id) ON DELETE CASCADE
);

-- -- Inserir dados de exemplo
-- -- Usuários
-- INSERT INTO Usuario (nome, email, senha, perfil) VALUES
-- ('João Silva', 'joao@email.com', 'senha123', 'aluno'),
-- ('Maria Oliveira', 'maria@email.com', 'senha456', 'professor');
INSERT INTO Usuario (nome, email, senha, perfil) VALUES
('Admin EduLivre', 'admin@email.com', 'admin123', 'admin');

-- -- Cursos
-- INSERT INTO Curso (titulo, descricao, avaliacao) VALUES
-- ('Curso de Java', 'Aprenda Java do básico ao avançado.', '{"media": 0, "comentarios": []}'),
-- ('Curso de PostgreSQL', 'Domine o banco de dados PostgreSQL.', '{"media": 0, "comentarios": []}');

-- -- Matrículas
-- INSERT INTO Matricula (usuario_id, curso_id) VALUES
-- ((SELECT id FROM Usuario WHERE email = 'joao@email.com'), (SELECT id FROM Curso WHERE titulo = 'Curso de Java')),
-- ((SELECT id FROM Usuario WHERE email = 'joao@email.com'), (SELECT id FROM Curso WHERE titulo = 'Curso de PostgreSQL'));

-- -- Conteúdos
-- INSERT INTO Conteudo (curso_id, titulo, descricao, tipo) VALUES
-- ((SELECT id FROM Curso WHERE titulo = 'Curso de Java'), 'Introdução ao Java', 'Aula introdutória sobre Java.', 'video'),
-- ((SELECT id FROM Curso WHERE titulo = 'Curso de Java'), 'Documentação Oficial', 'Documentação oficial do Java em PDF.', 'pdf'),
-- ((SELECT id FROM Curso WHERE titulo = 'Curso de Java'), 'Exercícios Práticos', 'Lista de exercícios para praticar Java.', 'pdf'),
-- ((SELECT id FROM Curso WHERE titulo = 'Curso de Java'), 'Audio Explicativo', 'Explicação em áudio sobre conceitos básicos.', 'audio'),
-- ((SELECT id FROM Curso WHERE titulo = 'Curso de PostgreSQL'), 'Instalação do PostgreSQL', 'Guia de instalação do PostgreSQL.', 'slide'),
-- ((SELECT id FROM Curso WHERE titulo = 'Curso de PostgreSQL'), 'Comandos Básicos', 'Lista de comandos básicos do PostgreSQL.', 'quiz'),
-- ((SELECT id FROM Curso WHERE titulo = 'Curso de PostgreSQL'), 'Video Tutorial', 'Tutorial em vídeo sobre PostgreSQL.', 'video'),
-- ((SELECT id FROM Curso WHERE titulo = 'Curso de PostgreSQL'), 'Cheat Sheet', 'Folha de referência rápida de comandos SQL.', 'imagem');