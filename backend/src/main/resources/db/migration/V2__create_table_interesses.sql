CREATE TYPE frequencia_festas_enum AS ENUM ('NUNCA', 'AS_VEZES', 'FREQUENTE');
CREATE TYPE habitos_limpeza_enum AS ENUM ('DIARIO', 'SEMANAL', 'QUINZENAL', 'OCASIONAL');
CREATE TYPE horario_sono_enum AS ENUM ('MATUTINO', 'VESPERTINO', 'NOTURNO', 'FLEXIVEL');

CREATE TABLE interesses (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE,
    hobbies TEXT,
    frequencia_festas frequencia_festas_enum,
    habitos_limpeza habitos_limpeza_enum,
    aceita_pets BOOLEAN,
    horario_sono horario_sono_enum,
    orcamento_min DECIMAL,
    orcamento_max DECIMAL,
    aceita_dividir_quarto BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES usuarios(id)
);
