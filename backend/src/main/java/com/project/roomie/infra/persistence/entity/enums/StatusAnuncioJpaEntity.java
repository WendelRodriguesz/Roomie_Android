package com.project.roomie.infra.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusAnuncioJpaEntity {
    ATIVO("ATIVO"),
    PAUSADO("PAUSADO");

    private final String statusAnuncio;
}
