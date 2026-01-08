package com.project.roomie.core.model.enums;

public enum Comodo {

    SALA_DE_ESTAR("SALA_DE_ESTAR"),
    SALA_DE_JANTAR("SALA_DE_JANTAR"),
    COZINHA("COZINHA"),
    BANHEIRO("BANHEIRO"),
    QUARTO("QUARTO"),
    LAVANDERIA("LAVANDERIA"),
    GARAGEM("GARAGEM"),
    VARANDA("VARANDA");

    private final String comodo;
    Comodo(String comodo) {
        this.comodo = comodo;
    }

    public String getComodo() {
        return comodo;
    }
}