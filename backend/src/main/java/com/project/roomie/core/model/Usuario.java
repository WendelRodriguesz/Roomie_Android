package com.project.roomie.core.model;

import com.project.roomie.core.model.enums.Role;
import java.time.LocalDate;

public class Usuario {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private LocalDate data_de_nascimento;
    private Role role;

    public Usuario() {
    }

    public Usuario(Integer id, String nome, String email, String senha, LocalDate data_de_nascimento, Role role) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.data_de_nascimento = data_de_nascimento;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}