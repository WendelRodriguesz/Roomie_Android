package com.project.roomie.infra.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FrequenciaFestasJpaEntity {
    NUNCA("NUNCA"),
    AS_VEZES("AS_VEZES"),
    FREQUENTE("FREQUENTE");

    private final String frequencia_festas;
}