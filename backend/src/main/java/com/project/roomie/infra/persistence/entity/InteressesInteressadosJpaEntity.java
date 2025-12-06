package com.project.roomie.infra.persistence.entity;

import com.project.roomie.infra.persistence.entity.enums.Frequencia_festasJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.Habitos_limpezaJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.Horario_sonoJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "interesses_interessados")
public class InteressesInteressadosJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequencia_festasJpaEntity frequencia_festas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Habitos_limpezaJpaEntity habitos_limpeza;

    @Column(nullable = false)
    private boolean aceita_pets;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Horario_sonoJpaEntity horario_sono;

    @Column(nullable = false)
    private float orcamento_min;

    @Column(nullable = false)
    private float orcamento_max;

    @Column(nullable = false)
    private boolean aceita_dividir_quarto;
}