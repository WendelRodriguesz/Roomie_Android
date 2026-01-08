package com.project.roomie.ports.in;

import com.project.roomie.core.model.InteressesInteressados;
import com.project.roomie.dto.response.InteressesInteressadosResponseDTO;
import com.project.roomie.dto.update.InteressesInteressadosUpdateDTO;

public interface InteressesInteressadosPortIn {
    InteressesInteressados cadastrar(InteressesInteressados interessesInteressados,
                                     Integer id_usuario);
    InteressesInteressadosResponseDTO atualizar(Integer id, InteressesInteressadosUpdateDTO interessesInteressadosUpdateDTO);

}