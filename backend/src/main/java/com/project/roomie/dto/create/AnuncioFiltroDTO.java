package com.project.roomie.dto.create;

import com.project.roomie.core.model.enums.TipoImovel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnuncioFiltroDTO {

    private String cidade;
    private String bairro;
    private Float custoMin;
    private Float custoMax;
    private TipoImovel tipoImovel;
    private Integer vagasMin;
    private Boolean aceitaPets;
    private Boolean aceitaDividirQuarto;
}
