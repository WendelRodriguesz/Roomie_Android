package com.project.roomie.core.model.enums;

public enum Habitos_limpeza {
    DIARIO("DIARIO"),
    SEMANAL("SEMANAL"),
    QUINZENAL("QUINZENAL"),
    OCASIONAL("OCASIONAL");

    private final String habitos_limpeza;

    Habitos_limpeza(String habitos_limpeza) {
        this.habitos_limpeza = habitos_limpeza;
    }

    public String getHabitos_limpeza() {
        return habitos_limpeza;
    }
}
