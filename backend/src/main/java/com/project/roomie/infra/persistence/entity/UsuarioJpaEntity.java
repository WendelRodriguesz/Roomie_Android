package com.project.roomie.infra.persistence.entity;

import com.project.roomie.core.model.enums.Role;
import com.project.roomie.infra.persistence.entity.enums.GeneroJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.RoleJpaEntity;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@Table(name = "usuarios")
public class UsuarioJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(nullable = false)
    private LocalDate data_de_nascimento;

    @Column(nullable = false)
    private Integer idade;

    @Column(nullable = false, length = 50)
    private String cidade;

    @Column(nullable = false, length = 100)
    private String ocupacao;

    @Column(nullable = false, length = 255)
    private String bio;

    @Column(nullable = false, length = 50)
    private GeneroJpaEntity genero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50, insertable = false, updatable = false)
    private RoleJpaEntity role;

    @Column(length = 500)
    private String foto_de_perfil;
}