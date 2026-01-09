package com.project.roomie.ports.out;

import com.project.roomie.core.model.Chat;

import java.util.List;

public interface ChatPortOut {

    Chat save(Chat chat);
    List<Chat> findAllById_ofertante(Integer id_ofertante);
    List<Chat> findAllById_interessado(Integer id_interessado);
}