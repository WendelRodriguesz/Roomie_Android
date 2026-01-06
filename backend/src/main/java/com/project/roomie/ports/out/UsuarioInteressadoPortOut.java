package com.project.roomie.ports.out;

import com.project.roomie.core.model.UsuarioInteressado;

public interface UsuarioInteressadoPortOut {

    UsuarioInteressado save(UsuarioInteressado usuarioInteressado);
    UsuarioInteressado findById(Integer id);
}