package com.project.roomie.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioInteressadoCreateDTO {

    private String nome;
    private String email;
    private String senha;
    private String data_de_nascimento;
}