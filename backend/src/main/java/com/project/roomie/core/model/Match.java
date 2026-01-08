package com.project.roomie.core.model;

import com.project.roomie.core.model.enums.MatchStatus;

public class Match {

    private Integer id;
    private UsuarioInteressado interessado;
    private UsuarioOfertante ofertante;
    private MatchStatus status;

    public Match() {
    }

    public Match(UsuarioInteressado interessado,
                 UsuarioOfertante ofertante,
                 MatchStatus status) {
        this.interessado = interessado;
        this.ofertante = ofertante;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public UsuarioOfertante getOfertante() {
        return ofertante;
    }

    public void setOfertante(UsuarioOfertante ofertante) {
        this.ofertante = ofertante;
    }

    public UsuarioInteressado getInteressado() {
        return interessado;
    }

    public void setInteressado(UsuarioInteressado interessado) {
        this.interessado = interessado;
    }

    public void aceitar() {
        this.status = MatchStatus.ACEITO;
    }

    public void recusar() {
        this.status = MatchStatus.RECUSADO;
    }
}