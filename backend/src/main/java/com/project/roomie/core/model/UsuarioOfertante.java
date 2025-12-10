package com.project.roomie.core.model;

import com.project.roomie.core.model.enums.Genero;
import com.project.roomie.core.model.enums.Role;

import java.time.LocalDate;

public class UsuarioOfertante extends Usuario {

    private Anuncio anuncio;

    public UsuarioOfertante(Integer id,
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
                            Anuncio anuncio){
        this.anuncio = anuncio;
        super(id, nome, email, senha, data_de_nascimento, idade, cidade, ocupacao, bio, genero, role, foto_de_perfil);
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }
}