package com.project.roomie.ports.in;

import com.project.roomie.core.model.Chat;
import com.project.roomie.dto.response.ChatResponseDTO;

import java.util.List;

public interface ChatPortIn {

    Chat cadastrarChat(Chat chat);
    List<ChatResponseDTO> visualizarTodos(Integer id_usuario);
}