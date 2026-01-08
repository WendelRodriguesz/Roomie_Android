package com.project.roomie.ports.in;

import com.project.roomie.core.model.InteressesOfertantes;
import com.project.roomie.dto.response.InteressesOfertantesResponseDTO;
import com.project.roomie.dto.update.InteressesOfertantesUpdateDTO;

public interface InteressesOfertantesPortIn {
    InteressesOfertantes cadastrarInteresses(InteressesOfertantes interessesOfertantes,
                                             Integer id_usuario);
    InteressesOfertantesResponseDTO atualizar(Integer id, InteressesOfertantesUpdateDTO interessesOfertantesUpdateDTO);

}
