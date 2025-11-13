package com.project.roomie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioInteressadoResponseDTO {

    private String nome;
    private String email;
    private String data_de_nascimento;
}