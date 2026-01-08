package com.project.roomie.dto.update;

import com.project.roomie.core.model.enums.Comodo;
import com.project.roomie.core.model.enums.TipoImovel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnuncioUpdateDTO {
    private String titulo;
    private String descricao;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private Float valorAluguel;
    private Float valorContas;
    private Integer vagasDisponiveis;
    private TipoImovel tipo_imovel;
    private List<Comodo> comodos;
}
