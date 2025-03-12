DROP DATABASE IF EXISTS spotifraco;

CREATE DATABASE spotifraco;

\c spotifraco;

CREATE TABLE
    usuario (
        id SERIAL PRIMARY KEY,
        nome TEXT UNIQUE NOT NULL,
        email TEXT UNIQUE NOT NULL,
        senha TEXT NOT NULL
    );

CREATE TABLE
    artista (
        id SERIAL PRIMARY KEY,
        nome TEXT NOT NULL,
        nome_artistico CHARACTER VARYING(60)
    );

CREATE TABLE
    album (
        id SERIAL PRIMARY KEY,
        titulo TEXT NOT NULL,
        data_lancamento DATE,
        artista_id INTEGER REFERENCES artista (id)
    );

CREATE TABLE
    musica (
        id SERIAL PRIMARY KEY,
        titulo TEXT NOT NULL,
        duracao INTEGER CHECK (duracao > 0),
        album_id INTEGER REFERENCES album (id)
    );

CREATE TABLE
    playlist (
        id SERIAL PRIMARY KEY,
        nome TEXT NOT NULL,
        data_hora TIMESTAMP DEFAULT current_timestamp,
        usuario_id INTEGER REFERENCES usuario (id)
    );

CREATE TABLE
    playlist_musica (
        playlist_id INTEGER REFERENCES playlist (id),
        musica_id INTEGER REFERENCES musica (id),
        PRIMARY KEY (playlist_id, musica_id)
    );

INSERT INTO
    usuario (nome, email, senha)
VALUES
    (
        'André',
        'andre.souza@aluno.riogrande.ifrs.edu.br',
        md5 ('13245678')
    ),
    (
        'Igor',
        'igor.pereira@riogrande.ifrs.edu.br',
        md5 ('12345678')
    );
    
INSERT INTO
    artista (nome, nome_artistico)
VALUES
    ('Gustavo', 'Black Alien'),
    ('Leandro', 'Emicida'),
    ('Kendrick Lamar Morale', 'Kendrick Lamar'),
    ('Tyler Okonma', 'Tyler, the Creator');

INSERT INTO
    album (titulo, artista_id)
VALUES
    ('Abaixo de Zero: Hello Hell', 1),
    ('AmarElo', 2),
    ('DAMN.', 3),
    ('To Pimp a Butterfly', 4);

INSERT INTO
    musica (titulo, duracao, album_id)
VALUES
    ('Carta para Amy', 166, 1),
    ('Área 51', 263, 1),
    ('Pequenas Alegrias da Vida Adulta', 293, 2),
    ('AmarElo', 324, 2),
    ('XXX.', 254, 3),
    ('DUCKWORTH.', 249, 3),
    ('LOVE.', 213, 3),
    ('PRIDE.', 275, 3),
    ('LUST.', 308, 3),
    ('HUMBLE.', 177, 3),
    ('DNA.', 186, 3),
    ('Alright', 219, 4),
    ('King Kunta', 235, 4);

INSERT INTO
    playlist (nome, usuario_id)
values
    ('RAP-AIZ', 1),
    ('RAP IT UP', 1),
    ('minhas canções', 2),
    ('emo', 2),
    ('samba 90', 2);

INSERT INTO
    playlist_musica (playlist_id, musica_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (2, 5),
    (2, 6),
    (2, 7),
    (2, 8),
    (2, 9),
    (2, 10),
    (2, 11),
    (2, 12),
    (2, 13);
