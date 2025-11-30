package com.project.roomie.core.model.enums;

public enum Horario_sono {
    MATUTINO("MATUTINO"),
    VESPERTINO("VESPERTINO"),
    NOTURNO("NOTURNO"),
    FLEXIVEL("FLEXIVEL");

    private final String horarrio_sono;

    Horario_sono(String horarrio_sono) {
        this.horarrio_sono = horarrio_sono;
    }
    public String getHorarrio_sono() {
        return horarrio_sono;
    }
}
