package com.project.roomie.mapper;

import com.project.roomie.core.model.Usuario;
import com.project.roomie.infra.persistence.entity.UsuarioJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario JpaEntitytoModel(UsuarioJpaEntity usuarioJpaEntity);
}