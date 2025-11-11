package com.project.roomie.infra.persistence.entity.enums;

import lombok.Getter;

@Getter
public enum RoleJpaEntity {

    INTERESSADO("ADMIN"),
    OFERTANTE("RECEPCIONISTA");

    private final String role;

    RoleJpaEntity(String role) {
        this.role = role;
    }
}