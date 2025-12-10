package com.project.roomie.core.model.enums;

public enum FrequenciaFestas {
    NUNCA("NUNCA"),
    AS_VEZES("AS_VEZES"),
    FREQUENTE("FREQUENTE");

    private final String frequencia_festas;

    FrequenciaFestas(String frequencia_festas) {
        this.frequencia_festas = frequencia_festas;
    }

    public String getFrequencia_festas() {
        return frequencia_festas;
    }
}