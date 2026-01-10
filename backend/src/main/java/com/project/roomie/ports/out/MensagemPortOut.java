package com.project.roomie.ports.out;

import com.project.roomie.core.model.Mensagem;

public interface MensagemPortOut {

    Mensagem save(Mensagem mensagem);
}