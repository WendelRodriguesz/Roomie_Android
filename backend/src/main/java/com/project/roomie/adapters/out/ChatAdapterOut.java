package com.project.roomie.adapters.out;

import com.project.roomie.core.model.Chat;
import com.project.roomie.infra.persistence.repository.ChatRepository;
import com.project.roomie.mapper.ChatMapper;
import com.project.roomie.ports.out.ChatPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatAdapterOut implements ChatPortOut {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Autowired
    public ChatAdapterOut(ChatRepository chatRepository,
                          ChatMapper chatMapper){
        this.chatRepository = chatRepository;
        this.chatMapper = chatMapper;
    }

    @Override
    public Chat save(Chat chat){
        return chatMapper.JpaEntitytoModel(chatRepository.save(chatMapper.ModeltoJpaEntity(chat)));
    }

    public List<Chat> findAllById_ofertante(Integer id_ofertante){
        return chatMapper.JpaEntitytoModel(chatRepository.findAllById_ofertante(id_ofertante));
    }

    public List<Chat> findAllById_interessado(Integer id_interessado){
        return chatMapper.JpaEntitytoModel(chatRepository.findAllById_interessado(id_interessado));
    }
}