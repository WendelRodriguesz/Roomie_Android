package com.project.roomie.infra.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ComodoJpaEntity {

    SALA_DE_ESTAR("SALA_DE_ESTAR"),
    SALA_DE_JANTAR("SALA_DE_JANTAR"),
    COZINHA("COZINHA"),
    BANHEIRO("BANHEIRO"),
    QUARTO("QUARTO"),
    LAVANDERIA("LAVANDERIA"),
    GARAGEM("GARAGEM"),
    VARANDA("VARANDA");

    private final String comodo;
}