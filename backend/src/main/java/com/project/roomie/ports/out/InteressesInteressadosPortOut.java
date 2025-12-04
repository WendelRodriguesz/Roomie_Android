package com.project.roomie.ports.out;

import com.project.roomie.core.model.InteressesInteressados;
import com.project.roomie.infra.persistence.entity.InteressesInteressadosJpaEntity;

public interface InteressesInteressadosPortOut {
    InteressesInteressados save(InteressesInteressadosJpaEntity interessesInteressadosJpaEntity);

}
