package com.project.roomie.ports.out;

import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.infra.persistence.entity.UsuarioInteressadoJpaEntity;

import java.util.Optional;

public interface UsuarioInteressadoPortOut {

    UsuarioInteressado save(UsuarioInteressadoJpaEntity usuarioInteressadoJpaEntity);
    UsuarioInteressado findById(Integer id);
}