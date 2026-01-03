package com.project.roomie.infra.persistence.entity;

import com.project.roomie.infra.persistence.entity.enums.MatchStatusJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matches")
public class MatchJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_interessado", nullable = false)
    private UsuarioInteressadoJpaEntity interessado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ofertante", nullable = false)
    private UsuarioOfertanteJpaEntity ofertante;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatusJpaEntity status;
}