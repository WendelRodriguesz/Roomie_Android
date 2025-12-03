package com.project.roomie.infra.persistence.entity;

import com.project.roomie.infra.persistence.entity.enums.Frequencia_festasJPAEntity;
import com.project.roomie.infra.persistence.entity.enums.Habitos_limpezaJPAEntity;
import com.project.roomie.infra.persistence.entity.enums.Horario_sonoJPAEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "interesses_interessados")
public class InteressesJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UsuarioJpaEntity user_id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequencia_festasJPAEntity frequencia_festas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Habitos_limpezaJPAEntity habitos_limpeza;

    @Column(nullable = false)
    private boolean aceita_pets;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Horario_sonoJPAEntity horario_sono;

    @Column(nullable = false)
    private float orcamento_min;

    @Column(nullable = false)
    private float orcamento_max;

    @Column(nullable = false)
    private boolean aceita_dividir_quarto;

}
