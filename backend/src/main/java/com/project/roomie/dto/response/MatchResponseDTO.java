package com.project.roomie.dto.response;

import com.project.roomie.core.model.enums.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MatchResponseDTO {

    private Integer id;
    private UsuarioInteressadoResponseDTO interessado;
    private UsuarioOfertanteResponseDTO ofertante;
    private MatchStatus status;
}