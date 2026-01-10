package com.project.roomie.ports.out;

import com.project.roomie.core.model.InteressesInteressados;

public interface InteressesInteressadosPortOut {
    InteressesInteressados save(InteressesInteressados interessesInteressados);
    InteressesInteressados findById(Integer id);
}
