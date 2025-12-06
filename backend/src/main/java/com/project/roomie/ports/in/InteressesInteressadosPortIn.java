package com.project.roomie.ports.in;

import com.project.roomie.core.model.InteressesInteressados;

public interface InteressesInteressadosPortIn {
    InteressesInteressados cadastrarInteresses(InteressesInteressados interessesInteressados,
                                               Integer id_usuario);

}