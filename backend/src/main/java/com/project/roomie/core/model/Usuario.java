package com.project.roomie.core.model;

import com.project.roomie.core.model.enums.Genero;
import com.project.roomie.core.model.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class Usuario implements UserDetails {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private LocalDate data_de_nascimento;
    private Integer idade;
    private String cidade;
    private String ocupacao;
    private String bio;
    private Genero genero;
    private Role role;
    private String foto_de_perfil;
    private String firebase_token;

    public Usuario() {
    }

    public Usuario(Integer id,
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
                   String firebase_token) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.data_de_nascimento = data_de_nascimento;
        this.idade = idade;
        this.cidade = cidade;
        this.ocupacao = ocupacao;
        this.bio = bio;
        this.genero = genero;
        this.role = role;
        this.foto_de_perfil = foto_de_perfil;
        this.firebase_token = firebase_token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getData_de_nascimento() {
        return data_de_nascimento;
    }

    public void setData_de_nascimento(LocalDate data_de_nascimento) {
        this.data_de_nascimento = data_de_nascimento;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFoto_de_perfil(){
        return foto_de_perfil;
    }

    public void setFoto_de_perfil(String foto_de_perfil) {
        this.foto_de_perfil = foto_de_perfil;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }
}