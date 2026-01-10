package com.project.roomie.adapters.in;

import com.project.roomie.dto.response.ChatResponseDTO;
import com.project.roomie.mapper.ChatMapper;
import com.project.roomie.ports.in.ChatPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatAdapterIn {

    private final ChatPortIn chatPortIn;
    private final ChatMapper chatMapper;

    @Autowired
    public ChatAdapterIn(ChatPortIn chatPortIn,
                         ChatMapper chatMapper) {
        this.chatPortIn = chatPortIn;
        this.chatMapper = chatMapper;
    }

    @GetMapping("/visualizarMeusChats/{id_usuario}")
    public List<ChatResponseDTO> visualizarTodos(@PathVariable Integer id_usuario){
        return chatPortIn.visualizarTodos(id_usuario);
    }
}