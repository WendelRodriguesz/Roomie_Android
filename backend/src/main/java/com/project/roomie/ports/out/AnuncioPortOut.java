package com.project.roomie.ports.out;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.infra.persistence.entity.AnuncioJpaEntity;

import java.util.List;

public interface AnuncioPortOut {
    Anuncio save(Anuncio anuncio);
    Anuncio findById(Integer id);
    List<Anuncio> buscarAnunciosAtivos();
}