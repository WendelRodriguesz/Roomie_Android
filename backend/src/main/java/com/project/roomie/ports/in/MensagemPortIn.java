package com.project.roomie.ports.in;

import com.project.roomie.core.model.Mensagem;

public interface MensagemPortIn {

    Mensagem cadastrarMensagem(Mensagem mensagem);
}