package com.project.roomie.dto;

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
public class UsuarioOfertanteDTO {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private LocalDate data_de_nascimento;
    private Role role;
}