package com.project.roomie.infra.persistence.entity;

import com.project.roomie.infra.persistence.entity.enums.ComodoJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.StatusAnuncioJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.TipoImovelJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "anuncios")
public class AnuncioJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, length = 255)
    private String rua;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(nullable = false, length = 150)
    private String bairro;

    @Column(nullable = false, length = 150)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(nullable = false)
    private float valor_aluguel;

    @Column(nullable = false)
    private float valor_contas;

    @Column(nullable = false)
    private int vagas_disponiveis;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoImovelJpaEntity tipo_imovel;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "anuncio_fotos",
            joinColumns = @JoinColumn(name = "anuncio_id")
    )
    @Column(name = "url_foto")
    private List<String> fotos;

    @ElementCollection(targetClass = ComodoJpaEntity.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "anuncio_comodos",
            joinColumns = @JoinColumn(name = "anuncio_id"))
    @Column(name = "comodo")
    private List<ComodoJpaEntity> comodos;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusanuncio", nullable = false)
    private StatusAnuncioJpaEntity statusAnuncio;

    @OneToOne(mappedBy = "anuncio")
    private UsuarioOfertanteJpaEntity ofertante;
}