package com.project.roomie.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MensagemCreateDTO {

    private Integer id_chat;
    private Integer id_remetente;
    private Integer id_destinatario;
    private String conteudo;
}