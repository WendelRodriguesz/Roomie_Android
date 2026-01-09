package com.project.roomie.core.service;

import com.project.roomie.core.model.Chat;
import com.project.roomie.ports.in.ChatPortIn;
import com.project.roomie.ports.out.ChatPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService implements ChatPortIn {

    private final ChatPortOut chatPortOut;

    @Autowired
    public ChatService(ChatPortOut chatPortOut){
        this.chatPortOut = chatPortOut;
    }

    @Override
    public Chat cadastrarChat(Chat chat){
        return chatPortOut.save(chat);
    }
}