CREATE TABLE chats (
    id SERIAL PRIMARY KEY,
    id_interessado INTEGER NOT NULL,
    FOREIGN KEY (id_interessado) REFERENCES usuarios(id),
    id_ofertante INTEGER NOT NULL,
    FOREIGN KEY (id_ofertante) REFERENCES usuarios(id),
    usado_em TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uk_chat UNIQUE (id_interessado, id_ofertante)
);