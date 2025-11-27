package com.project.roomie.infra.persistence.entity;

import com.project.roomie.infra.persistence.entity.enums.GeneroJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.RoleJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("INTERESSADO")
@Entity
public class UsuarioInteressadoJpaEntity extends UsuarioJpaEntity{

    public UsuarioInteressadoJpaEntity(Integer id,
                                       String nome,
                                       String email,
                                       String senha,
                                       LocalDate data_de_nascimento,
                                       Integer idade,
                                       String cidade,
                                       String ocupacao,
                                       String bio,
                                       GeneroJpaEntity genero,
                                       RoleJpaEntity role,
                                       String foto_de_perfil){
        super(id, nome, email, senha, data_de_nascimento, idade, cidade, ocupacao, bio, genero, role, foto_de_perfil);
    }
}