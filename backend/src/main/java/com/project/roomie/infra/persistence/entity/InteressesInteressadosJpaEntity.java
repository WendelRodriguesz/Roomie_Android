package com.project.roomie.infra.persistence.entity;

import com.project.roomie.infra.persistence.entity.enums.FrequenciaFestasJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.HabitosLimpezaJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.HorarioSonoJpaEntity;
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
    private FrequenciaFestasJpaEntity frequencia_festas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HabitosLimpezaJpaEntity habitos_limpeza;

    @Column(nullable = false)
    private boolean aceita_pets;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HorarioSonoJpaEntity horario_sono;

    @Column(nullable = false)
    private float orcamento_min;

    @Column(nullable = false)
    private float orcamento_max;

    @Column(nullable = false)
    private boolean aceita_dividir_quarto;

    @Column(nullable = false)
    private boolean fumante;

    @Column(nullable = false)
    private boolean consome_bebidas_alcoolicas;
}