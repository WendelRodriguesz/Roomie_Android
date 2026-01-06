package com.project.roomie.infra.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchStatusJpaEntity {

    PENDENTE("PENDENTE"),
    ACEITO("ACEITO"),
    RECUSADO("RECUSADO");

    private final String match_status;
}