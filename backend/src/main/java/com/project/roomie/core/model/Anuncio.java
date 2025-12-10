package com.project.roomie.core.model;

import com.project.roomie.core.model.enums.Comodo;
import com.project.roomie.core.model.enums.TipoImovel;

import java.util.List;

public class Anuncio {

    private Integer id;
    private String titulo;
    private String descricao;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private float valor_aluguel;
    private float valor_contas;
    private int vagas_disponiveis;
    private TipoImovel tipo_imovel;
    private List<String> fotos;
    private List<Comodo> comodos;

    public Anuncio() {
    }

    public Anuncio(Integer id,
                   String titulo,
                   String descricao,
                   String rua,
                   String numero,
                   String bairro,
                   String cidade,
                   String estado,
                   float valor_aluguel,
                   float valor_contas,
                   int vagas_disponiveis,
                   TipoImovel tipo_imovel,
                   List<String> fotos,
                   List<Comodo> comodos) {

        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.valor_aluguel = valor_aluguel;
        this.valor_contas = valor_contas;
        this.vagas_disponiveis = vagas_disponiveis;
        this.tipo_imovel = tipo_imovel;
        this.fotos = fotos;
        this.comodos = comodos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getValor_aluguel() {
        return valor_aluguel;
    }

    public void setValor_aluguel(float valor_aluguel) {
        this.valor_aluguel = valor_aluguel;
    }

    public float getValor_contas() {
        return valor_contas;
    }

    public void setValor_contas(float valor_contas) {
        this.valor_contas = valor_contas;
    }

    public int getVagas_disponiveis() {
        return vagas_disponiveis;
    }

    public void setVagas_disponiveis(int vagas_disponiveis) {
        this.vagas_disponiveis = vagas_disponiveis;
    }

    public TipoImovel getTipo_imovel() {
        return tipo_imovel;
    }

    public void setTipo_imovel(TipoImovel tipo_imovel) {
        this.tipo_imovel = tipo_imovel;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public List<Comodo> getComodos() {
        return comodos;
    }

    public void setComodos(List<Comodo> comodos) {
        this.comodos = comodos;
    }
}