package com.project.roomie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioOfertanteResponseDTO {

    private String nome;
    private String email;
    private String data_de_nascimento;
}