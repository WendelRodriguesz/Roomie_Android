package com.project.roomie.infra.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleJpaEntity {

    INTERESSADO("INTERESSADO"),
    OFERTANTE("OFERTANTE");

    private final String role;
}