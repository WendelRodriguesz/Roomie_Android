package com.project.roomie.core.model.enums;

public enum Horario_sono {
    MATUTINO("MATUTINO"),
    VESPERTINO("VESPERTINO"),
    NOTURNO("NOTURNO"),
    FLEXIVEL("FLEXIVEL");

    private final String horario_sono;

    Horario_sono(String horario_sono) {
        this.horario_sono = horario_sono;
    }

    public String getHorario_sono() {
        return horario_sono;
    }
}