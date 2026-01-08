package com.project.roomie.core.model.enums;

public enum Genero {

    MASCULINO("MASCULINO"),
    FEMININO("FEMININO"),
    OUTROS("OUTROS");

    private final String genero;

    Genero(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }
}