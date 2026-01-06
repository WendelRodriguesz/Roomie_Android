package com.project.roomie.ports.out;

import com.project.roomie.core.model.UsuarioOfertante;

import java.util.List;

public interface UsuarioOfertantePortOut {

    UsuarioOfertante save(UsuarioOfertante usuarioOfertante);
    UsuarioOfertante findById(Integer id);
    List<UsuarioOfertante> buscarCandidatosMatch(Integer id_usuario_interessado);
}