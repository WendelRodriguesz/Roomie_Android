package com.project.roomie.ports.out;

import com.project.roomie.core.model.Mensagem;

import java.util.List;

public interface MensagemPortOut {

    Mensagem save(Mensagem mensagem);
    List<Mensagem> findAllById_chat(Integer id_chat);
}