CREATE TABLE matches (
    id SERIAL PRIMARY KEY,
    id_interessado INTEGER NOT NULL,
    FOREIGN KEY (id_interessado) REFERENCES usuarios(id),
    id_ofertante INTEGER NOT NULL,
    FOREIGN KEY (id_ofertante) REFERENCES usuarios(id),
    status VARCHAR(50) NOT NULL,
    CONSTRAINT uk_match UNIQUE (id_interessado, id_ofertante)
);