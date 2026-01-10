package com.project.roomie.ports.in;

import com.project.roomie.core.model.Mensagem;
import com.project.roomie.dto.response.MensagemResponseDTO;

import java.util.List;

public interface MensagemPortIn {

    Mensagem cadastrarMensagem(Mensagem mensagem);
    List<MensagemResponseDTO> visualizarMensagens(Integer id_chat);
}