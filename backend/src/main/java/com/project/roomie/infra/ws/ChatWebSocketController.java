package com.project.roomie.infra.ws;

import com.project.roomie.core.service.ChatService;
import com.project.roomie.core.service.MensagemService;
import com.project.roomie.dto.create.MensagemCreateDTO;
import com.project.roomie.mapper.MensagemMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MensagemService mensagemService;
    private final MensagemMapper mensagemMapper;
    private final ChatService chatService;

    public ChatWebSocketController(SimpMessagingTemplate messagingTemplate,
                                   MensagemService mensagemService,
                                   MensagemMapper mensagemMapper,
                                   ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.mensagemService = mensagemService;
        this.mensagemMapper = mensagemMapper;
        this.chatService = chatService;
    }

    @MessageMapping("/chat/enviar")
    public void sendMessage(MensagemCreateDTO mensagem) {

        // canal privado do receptor
        String destino = "/queue/chat/" + mensagem.getId_chat();

        // persiste mensagem
        mensagemService.cadastrarMensagem(mensagemMapper.CreateDTOtoModel(mensagem));
        chatService.atualizarUso(mensagem.getId_chat());

        messagingTemplate.convertAndSend(destino, mensagem);
    }
}