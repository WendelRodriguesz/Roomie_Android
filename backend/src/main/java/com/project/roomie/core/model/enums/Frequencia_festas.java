package com.project.roomie.core.model.enums;

public enum Frequencia_festas {
    NUNCA("NUNCA"),
    AS_VEZES("AS_VEZES"),
    FREQUENTE("FREQUENTE");

    private final String frequencia_festas;
    Frequencia_festas(String frequencia_festas) {
        this.frequencia_festas = frequencia_festas;
    }
    public String getFrequencia_festas() {
        return frequencia_festas;
    }
}
