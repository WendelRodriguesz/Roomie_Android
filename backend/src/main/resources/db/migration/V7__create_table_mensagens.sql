CREATE TABLE mensagens (
    id SERIAL PRIMARY KEY,
    id_chat INTEGER NOT NULL,
    FOREIGN KEY (id_chat) REFERENCES chats(id),
    id_remetente INTEGER NOT NULL,
    FOREIGN KEY (id_remetente) REFERENCES usuarios(id),
    id_destinatario INTEGER NOT NULL,
    FOREIGN KEY (id_destinatario) REFERENCES usuarios(id),
    conteudo TEXT NOT NULL,
    enviada_em TIMESTAMP NOT NULL DEFAULT now()
);