package com.project.roomie.core.model;

import java.time.LocalDateTime;

public class Mensagem {

    private Integer id;
    private Integer id_chat;
    private Integer id_remetente;
    private Integer id_destinatario;
    private String conteudo;
    private LocalDateTime enviada_em;

    public Mensagem(){
    }

    public Mensagem(Integer id, Integer id_chat, Integer id_remetente, Integer id_destinatario, String conteudo, LocalDateTime enviada_em) {
        this.id = id;
        this.id_chat = id_chat;
        this.id_remetente = id_remetente;
        this.id_destinatario = id_destinatario;
        this.conteudo = conteudo;
        this.enviada_em = enviada_em;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_chat() {
        return id_chat;
    }

    public void setId_chat(Integer id_chat) {
        this.id_chat = id_chat;
    }

    public Integer getId_remetente() {
        return id_remetente;
    }

    public void setId_remetente(Integer id_remetente) {
        this.id_remetente = id_remetente;
    }

    public Integer getId_destinatario() {
        return id_destinatario;
    }

    public void setId_destinatario(Integer id_destinatario) {
        this.id_destinatario = id_destinatario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getEnviada_em() {
        return enviada_em;
    }

    public void setEnviada_em(LocalDateTime enviada_em) {
        this.enviada_em = enviada_em;
    }
}