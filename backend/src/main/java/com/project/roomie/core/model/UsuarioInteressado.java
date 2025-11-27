package com.project.roomie.core.model;

import com.project.roomie.core.model.enums.Genero;
import com.project.roomie.core.model.enums.Role;
import com.project.roomie.infra.persistence.entity.enums.GeneroJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.RoleJpaEntity;

import java.time.LocalDate;

public class UsuarioInteressado extends Usuario {

    public UsuarioInteressado(Integer id,
                                       String nome,
                                       String email,
                                       String senha,
                                       LocalDate data_de_nascimento,
                                       Integer idade,
                                       String cidade,
                                       String ocupacao,
                                       String bio,
                                       Genero genero,
                                       Role role,
                                       String foto_de_perfil){
        super(id, nome, email, senha, data_de_nascimento, idade, cidade, ocupacao, bio, genero, role, foto_de_perfil);
    }
}