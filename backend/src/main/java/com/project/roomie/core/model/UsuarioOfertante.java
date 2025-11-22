package com.project.roomie.core.model;

import com.project.roomie.core.model.enums.Role;

import java.time.LocalDate;

public class UsuarioOfertante extends Usuario {

    public UsuarioOfertante(Integer id, String nome, String email, String senha, LocalDate data_de_nascimento, Role role, String foto_de_perfil){
        super(id, nome, email, senha, data_de_nascimento, role, foto_de_perfil);
    }
}