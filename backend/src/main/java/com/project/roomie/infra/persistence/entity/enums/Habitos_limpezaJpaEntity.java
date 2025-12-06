package com.project.roomie.infra.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Habitos_limpezaJpaEntity {
    DIARIO("DIARIO"),
    SEMANAL("SEMANAL"),
    QUINZENAL("QUINZENAL"),
    OCASIONAL("OCASIONAL");

    private final String habitos_limpeza;

}