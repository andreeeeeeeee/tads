DROP DATABASE IF EXISTS parque;
CREATE DATABASE parque;

\c parque

DROP TABLE IF EXISTS visitante CASCADE;
CREATE TABLE visitante (
    id_visitante SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    data_nascimento DATE,
    email VARCHAR(50) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS atracao CASCADE;
CREATE TABLE atracao (
    id_atracao SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    capacidade INTEGER NOT NULL
);

DROP TABLE IF EXISTS ingresso CASCADE;
CREATE TABLE ingresso (
    id_ingresso SERIAL PRIMARY KEY,
    id_visitante INTEGER REFERENCES visitante(id_visitante),
    id_atracao INTEGER REFERENCES atracao(id_atracao),
    data_visita DATE NOT NULL
);

DROP TABLE IF EXISTS funcionario CASCADE;
CREATE TABLE funcionario (
    id_funcionario SERIAL PRIMARY KEY,
    id_atracao INTEGER REFERENCES atracao(id_atracao),
    nome VARCHAR(50) NOT NULL,
    salario DECIMAL(10, 2) NOT NULL,
    cargo VARCHAR(50) NOT NULL
);