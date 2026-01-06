package com.project.roomie.ports.out;

import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.infra.persistence.entity.UsuarioOfertanteJpaEntity;

public interface UsuarioOfertantePortOut {

    UsuarioOfertante save(UsuarioOfertante usuarioOfertante);
    UsuarioOfertante findById(Integer id);
}