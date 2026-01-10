package com.project.roomie.infra.ws;

import com.project.roomie.dto.create.MensagemCreateDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat/enviar")
    public void sendMessage(MensagemCreateDTO mensagem) {

        // canal privado do receptor
        String destino = "/queue/chat/" + mensagem.getId_chat();

        messagingTemplate.convertAndSend(destino, mensagem);
    }
}