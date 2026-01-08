package com.project.roomie.ports.out;

import com.project.roomie.core.model.InteressesOfertantes;
import com.project.roomie.infra.persistence.entity.InteressesOfertantesJpaEntity;

public interface InteressesOfertantesPortOut {
    InteressesOfertantes save(InteressesOfertantes interessesOfertantes);
    InteressesOfertantes findById(Integer id);
}
