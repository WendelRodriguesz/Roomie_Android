package com.project.roomie.core.model.enums;

public enum HorarioSono {
    MATUTINO("MATUTINO"),
    VESPERTINO("VESPERTINO"),
    NOTURNO("NOTURNO"),
    FLEXIVEL("FLEXIVEL");

    private final String horario_sono;

    HorarioSono(String horario_sono) {
        this.horario_sono = horario_sono;
    }

    public String getHorario_sono() {
        return horario_sono;
    }
}