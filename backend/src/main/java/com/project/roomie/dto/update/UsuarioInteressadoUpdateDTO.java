package com.project.roomie.dto.update;

import com.project.roomie.core.model.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioInteressadoUpdateDTO {
    private String nome;
    private String email;
    private String cidade;
    private String ocupacao;
    private String bio;
    private Genero genero;
}
