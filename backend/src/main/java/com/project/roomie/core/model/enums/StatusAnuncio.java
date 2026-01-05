package com.project.roomie.core.model.enums;

public enum StatusAnuncio {
    ATIVO("ATIVO"),
    PAUSADO("PAUSADO");

    private final String statusAnuncio;

    StatusAnuncio(String statusAnuncio) {
        this.statusAnuncio = statusAnuncio;
    }

    public String getStatusAnuncio() {
        return statusAnuncio;
    }
}
