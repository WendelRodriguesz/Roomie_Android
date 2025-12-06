package com.project.roomie.core.model;

import com.project.roomie.core.model.enums.Genero;
import com.project.roomie.core.model.enums.Role;

import java.time.LocalDate;

public class UsuarioInteressado extends Usuario {

    private InteressesInteressados interesses;

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
                                       String foto_de_perfil,
                                       InteressesInteressados interesses){
        this.interesses = interesses;
        super(id, nome, email, senha, data_de_nascimento, idade, cidade, ocupacao, bio, genero, role, foto_de_perfil);
    }

    public InteressesInteressados getInteresses() {
        return interesses;
    }

    public void setInteresses(InteressesInteressados interesses) {
        this.interesses = interesses;
    }
}