package com.project.roomie.ports.out;

import com.project.roomie.core.model.InteressesOfertantes;

public interface InteressesOfertantesPortOut {
    InteressesOfertantes save(InteressesOfertantes interessesOfertantes);
    InteressesOfertantes findById(Integer id);
}
