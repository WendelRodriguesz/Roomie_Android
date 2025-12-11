CREATE TABLE interesses_ofertantes (
    id SERIAL PRIMARY KEY,
    frequencia_festas VARCHAR(50),
    habitos_limpeza VARCHAR(50),
    aceita_pets BOOLEAN,
    horario_sono VARCHAR(50),
    aceita_dividir_quarto BOOLEAN
);