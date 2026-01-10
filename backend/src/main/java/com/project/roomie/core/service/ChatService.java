package com.project.roomie.core.service;

import com.project.roomie.core.model.Chat;
import com.project.roomie.core.model.Usuario;
import com.project.roomie.core.model.enums.Role;
import com.project.roomie.dto.response.ChatResponseDTO;
import com.project.roomie.mapper.ChatMapper;
import com.project.roomie.ports.in.ChatPortIn;
import com.project.roomie.ports.out.ChatPortOut;
import com.project.roomie.ports.out.UsuarioPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService implements ChatPortIn {

    private final ChatPortOut chatPortOut;
    private final UsuarioPortOut usuarioPortOut;
    private final ChatMapper chatMapper;

    @Autowired
    public ChatService(ChatPortOut chatPortOut,
                       UsuarioPortOut usuarioPortOut,
                       ChatMapper chatMapper){
        this.chatPortOut = chatPortOut;
        this.usuarioPortOut = usuarioPortOut;
        this.chatMapper = chatMapper;
    }

    @Override
    public Chat cadastrarChat(Chat chat){
        return chatPortOut.save(chat);
    }

    @Override
    public List<ChatResponseDTO> visualizarTodos(Integer id_usuario){

        Usuario usuario = usuarioPortOut.findById(id_usuario);
        List<ChatResponseDTO> chats;

        if (usuario.getRole() == Role.OFERTANTE){
            chats = chatMapper.ModeltoResponseDTO(
                    chatPortOut.findAllById_ofertante(id_usuario));

        } else if (usuario.getRole() == Role.INTERESSADO){
            chats = chatMapper.ModeltoResponseDTO(
                    chatPortOut.findAllById_interessado(id_usuario));

        }else{
            throw new RuntimeException("Usuário Inválido!");
        }

        return chats;
    }

    @Override
    public Chat atualizarUso(Integer id_chat){
        Chat chat = chatPortOut.findById(id_chat);
        chat.setUsado_em(LocalDateTime.now());
        return chatPortOut.save(chat);
    }
}