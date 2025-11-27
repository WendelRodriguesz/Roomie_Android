package com.project.roomie.dto.create;

import com.project.roomie.core.model.enums.Genero;
import com.project.roomie.core.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioInteressadoCreateDTO {

    private String nome;
    private String email;
    private String senha;
    private String data_de_nascimento;
    private String cidade;
    private String ocupacao;
    private String bio;
    private Genero genero;
}