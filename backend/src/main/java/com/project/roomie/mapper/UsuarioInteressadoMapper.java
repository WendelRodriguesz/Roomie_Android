package com.project.roomie.mapper;

import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.dto.UsuarioInteressadoDTO;
import com.project.roomie.infra.persistence.entity.UsuarioInteressadoJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioInteressadoMapper {

    UsuarioInteressado DTOtoModel(UsuarioInteressadoDTO usuarioInteressadoDTO);
    UsuarioInteressadoJpaEntity ModeltoJpaEntity(UsuarioInteressado usuarioInteressado);
}