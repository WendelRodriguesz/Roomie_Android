package com.project.roomie.core.model;

import com.project.roomie.core.model.enums.Role;

import java.time.LocalDate;

public class UsuarioInteressado extends Usuario {

    public UsuarioInteressado(Integer id, String nome, String email, String senha, LocalDate data_de_nascimento, Role role){
        super(id, nome, email, senha, data_de_nascimento, role);
    }
}