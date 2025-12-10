package com.project.roomie.ports.in;

import com.project.roomie.core.model.InteressesOfertantes;

public interface InteressesOfertantesPortIn {
    InteressesOfertantes cadastrarInteresses(InteressesOfertantes interessesOfertantes,
                                             Integer id_usuario);
}
