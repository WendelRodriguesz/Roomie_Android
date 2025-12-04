package com.project.roomie.mapper;

import com.project.roomie.core.model.InteressesInteressados;
import com.project.roomie.dto.create.InteressesInteressadosCreateDTO;
import com.project.roomie.dto.response.InteressesInteressadosResponseDTO;
import com.project.roomie.infra.persistence.entity.InteressesInteressadosJpaEntity;
import com.project.roomie.infra.persistence.entity.UsuarioJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InteressesInteressadosMapper {

    InteressesInteressados createDTOtoModel(InteressesInteressadosCreateDTO interessesInteressadosCreateDTO);

    InteressesInteressados jpaEntitytoModel(InteressesInteressadosJpaEntity interessesInteressadosJpaEntity);

    InteressesInteressadosResponseDTO modeltoResponseDTO(InteressesInteressados interessesInteressados);

    InteressesInteressados CreateDTOtoModel(InteressesInteressadosCreateDTO interessesInteressadosCreateDTO);

    InteressesInteressadosJpaEntity modeltoJpaEntity(InteressesInteressados interessesInteressados);

    // Conversão UsuarioJpaEntity → Integer
    default Integer map(UsuarioJpaEntity usuario) {
        return usuario != null ? usuario.getId() : null;
    }

    // Conversão Integer → UsuarioJpaEntity
    default UsuarioJpaEntity map(Integer id) {
        if (id == null) return null;
        UsuarioJpaEntity usuario = new UsuarioJpaEntity();
        usuario.setId(id);
        return usuario;
    }

}
