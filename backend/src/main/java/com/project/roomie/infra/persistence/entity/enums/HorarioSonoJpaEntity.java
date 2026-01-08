package com.project.roomie.infra.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HorarioSonoJpaEntity {
    MATUTINO("MATUTINO"),
    VESPERTINO("VESPERTINO"),
    NOTURNO("NOTURNO"),
    FLEXIVEL("FLEXIVEL");

    private final String horario_sono;
}