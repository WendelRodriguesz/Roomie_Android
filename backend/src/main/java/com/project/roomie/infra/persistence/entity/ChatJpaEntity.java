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
@Table(
        name = "chats",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_chat",
                        columnNames = {"id_interessado", "id_ofertante"}
                )
        }
)
public class ChatJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_interessado", nullable = false)
    private Integer id_interessado;

    @Column(name = "id_ofertante", nullable = false)
    private Integer id_ofertante;

    @Column(name = "usado_em", nullable = false)
    private LocalDateTime usado_em;
}