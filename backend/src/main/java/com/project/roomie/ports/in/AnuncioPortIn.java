package com.project.roomie.ports.in;

import com.project.roomie.core.model.Anuncio;

public interface AnuncioPortIn {
    Anuncio cadastrar(Anuncio anuncio, Integer id_usuario);
}