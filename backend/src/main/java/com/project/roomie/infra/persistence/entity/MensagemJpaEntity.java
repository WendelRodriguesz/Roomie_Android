package com.project.roomie.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mensagens")
public class MensagemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_chat", nullable = false)
    private Integer id_chat;

    @Column(name = "id_remetente", nullable = false)
    private Integer id_remetente;

    @Column(name = "id_destinatario", nullable = false)
    private Integer id_destinatario;

    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    @Column(name = "enviada_em", nullable = false)
    private LocalDateTime enviada_em;
}