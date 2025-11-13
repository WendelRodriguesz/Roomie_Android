package com.project.roomie.infra.persistence.entity;

import com.project.roomie.infra.persistence.entity.enums.RoleJpaEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("OFERTANTE")
@Entity
public class UsuarioOfertanteJpaEntity extends UsuarioJpaEntity{

    public UsuarioOfertanteJpaEntity(Integer id, String nome, String email, String senha, LocalDate data_de_nascimento, RoleJpaEntity role){
        super(id, nome, email, senha, data_de_nascimento, role);
    }
}