package com.project.roomie.ports.out;

import com.project.roomie.core.model.Interesses;
import com.project.roomie.infra.persistence.entity.InteressesJpaEntity;

public interface InteressesPortOut {
    Interesses save(InteressesJpaEntity interessesJpaEntity);

}
