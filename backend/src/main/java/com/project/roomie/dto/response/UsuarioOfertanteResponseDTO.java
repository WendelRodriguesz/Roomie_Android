package com.project.roomie.dto.response;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.core.model.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioOfertanteResponseDTO {

    private Integer id;
    private String nome;
    private String email;
    private String data_de_nascimento;
    private Integer idade;
    private String cidade;
    private String ocupacao;
    private String bio;
    private Genero genero;
    private String foto_de_perfil;
    private InteressesOfertantesResponseDTO interesses;
    private AnuncioResponseDTO anuncio;
}