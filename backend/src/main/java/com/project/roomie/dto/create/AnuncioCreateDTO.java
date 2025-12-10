package com.project.roomie.dto.create;

import com.project.roomie.core.model.enums.Comodo;
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
public class AnuncioCreateDTO {

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
    private List<Comodo> comodos;
}