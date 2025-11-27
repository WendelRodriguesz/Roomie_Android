CREATE TABLE usuarios(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    data_de_nascimento DATE NOT NULL,
    idade INT NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    ocupacao VARCHAR(100) NOT NULL,
    bio VARCHAR(255) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    foto_de_perfil VARCHAR(500)
);