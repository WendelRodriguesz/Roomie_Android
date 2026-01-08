CREATE TABLE anuncios (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    rua VARCHAR(255),
    numero VARCHAR(20),
    bairro VARCHAR(150),
    cidade VARCHAR(150),
    estado VARCHAR(2),
    valor_aluguel DECIMAL NOT NULL,
    valor_contas DECIMAL NOT NULL,
    vagas_disponiveis INTEGER,
    tipo_imovel VARCHAR(50) NOT NULL,
    statusAnuncio VARCHAR(50) NOT NULL
);

CREATE TABLE anuncio_comodos (
    id SERIAL PRIMARY KEY,
    anuncio_id INTEGER NOT NULL,
    comodo VARCHAR(50) NOT NULL,
    CONSTRAINT fk_anuncio_comodos
        FOREIGN KEY (anuncio_id) REFERENCES anuncios(id)
        ON DELETE CASCADE
);

CREATE TABLE anuncio_fotos (
    id SERIAL PRIMARY KEY,
    anuncio_id INTEGER NOT NULL,
    url_foto VARCHAR(500) NOT NULL,
    CONSTRAINT fk_anuncio_comodos
        FOREIGN KEY (anuncio_id) REFERENCES anuncios(id)
        ON DELETE CASCADE
);