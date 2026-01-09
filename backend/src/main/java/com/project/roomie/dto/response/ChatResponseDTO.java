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
public class ChatResponseDTO {

    private Integer id;
    private Integer id_interessado;
    private Integer id_ofertante;
    private LocalDateTime usado_em;
}