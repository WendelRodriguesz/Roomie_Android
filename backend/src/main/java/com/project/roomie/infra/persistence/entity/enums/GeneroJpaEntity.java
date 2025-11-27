package com.project.roomie.infra.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GeneroJpaEntity {

    MASCULINO("MASCULINO"),
    FEMININO("FEMININO"),
    OUTROS("OUTROS");

    private final String genero;
}