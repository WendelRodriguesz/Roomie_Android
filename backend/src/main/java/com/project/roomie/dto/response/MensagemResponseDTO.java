package com.project.roomie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MensagemResponseDTO {

    private Integer id;
    private Integer id_chat;
    private Integer id_remetente;
    private Integer id_destinatario;
    private String conteudo;
    private LocalDateTime enviada_em;
}