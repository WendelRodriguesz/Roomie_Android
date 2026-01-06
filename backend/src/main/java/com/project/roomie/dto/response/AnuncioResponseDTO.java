package com.project.roomie.dto.response;

import com.project.roomie.core.model.enums.Comodo;
import com.project.roomie.core.model.enums.StatusAnuncio;
import com.project.roomie.core.model.enums.TipoImovel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnuncioResponseDTO {

    private Integer id;
    private String titulo;
    private String descricao;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private float valor_aluguel;
    private float valor_contas;
    private int vagas_disponiveis;
    private TipoImovel tipo_imovel;
    private List<String> fotos;
    private List<Comodo> comodos;
    private StatusAnuncio status_anuncio;
}