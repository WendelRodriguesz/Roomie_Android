package com.project.roomie.infra.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoImovelJpaEntity {

    APARTAMENTO("APARTAMENTO"),
    CASA("CASA");

    private final String tipoImovel;
}