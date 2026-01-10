package com.project.roomie.ports.out;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.dto.create.AnuncioFiltroDTO;

import java.util.List;

public interface AnuncioPortOut {
    Anuncio save(Anuncio anuncio);
    Anuncio findById(Integer id);
    List<Anuncio> buscarAnunciosAtivos();
    List<Anuncio> buscarPorFiltro(AnuncioFiltroDTO anuncioFiltroDTO);
}