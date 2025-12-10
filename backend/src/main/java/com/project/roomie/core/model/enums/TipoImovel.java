package com.project.roomie.core.model.enums;

public enum TipoImovel {

    APARTAMENTO("APARTAMENTO"),
    CASA("CASA");

    private final String tipoImovel;

    TipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public String getTipoImovel() {
        return tipoImovel;
    }
}