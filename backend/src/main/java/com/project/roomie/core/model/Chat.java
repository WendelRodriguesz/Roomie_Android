package com.project.roomie.core.model;

import java.time.LocalDateTime;

public class Chat {

    private Integer id;
    private Integer id_interessado;
    private Integer id_ofertante;
    private LocalDateTime usado_em;

    public Chat(){
    }

    public Chat(Integer id, Integer id_interessado, Integer id_ofertante, LocalDateTime usado_em) {
        this.id = id;
        this.id_interessado = id_interessado;
        this.id_ofertante = id_ofertante;
        this.usado_em = usado_em;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_interessado() {
        return id_interessado;
    }

    public void setId_interessado(Integer id_interessado) {
        this.id_interessado = id_interessado;
    }

    public Integer getId_ofertante() {
        return id_ofertante;
    }

    public void setId_ofertante(Integer id_ofertante) {
        this.id_ofertante = id_ofertante;
    }

    public LocalDateTime getUsado_em() {
        return usado_em;
    }

    public void setUsado_em(LocalDateTime usado_em) {
        this.usado_em = usado_em;
    }
}