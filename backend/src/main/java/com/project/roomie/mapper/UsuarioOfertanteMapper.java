package com.project.roomie.mapper;

import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.dto.UsuarioInteressadoDTO;
import com.project.roomie.dto.UsuarioOfertanteDTO;
import com.project.roomie.infra.persistence.entity.UsuarioInteressadoJpaEntity;
import com.project.roomie.infra.persistence.entity.UsuarioOfertanteJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioOfertanteMapper {

    UsuarioOfertante DTOtoModel(UsuarioOfertanteDTO usuarioOfertanteDTO);
    UsuarioOfertanteJpaEntity ModeltoJpaEntity(UsuarioOfertante usuarioOfertante);
}