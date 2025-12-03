CREATE TABLE interesses_interessados (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE,
    frequencia_festas VARCHAR(50),
    habitos_limpeza VARCHAR(50),
    aceita_pets BOOLEAN,
    horario_sono VARCHAR(50),
    orcamento_min DECIMAL,
    orcamento_max DECIMAL,
    aceita_dividir_quarto BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES usuarios(id)

);
