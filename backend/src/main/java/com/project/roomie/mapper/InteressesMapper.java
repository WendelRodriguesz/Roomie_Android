package com.project.roomie.mapper;

import com.project.roomie.core.model.Interesses;
import com.project.roomie.dto.create.InteressesCreateDTO;
import com.project.roomie.dto.response.InteressesResponseDTO;
import com.project.roomie.infra.persistence.entity.InteressesJpaEntity;
import com.project.roomie.infra.persistence.entity.UsuarioJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InteressesMapper {

    Interesses createDTOtoModel(InteressesCreateDTO interessesCreateDTO);

    Interesses jpaEntitytoModel(InteressesJpaEntity interessesJpaEntity);

    InteressesResponseDTO modeltoResponseDTO(Interesses interesses);

    Interesses CreateDTOtoModel(InteressesCreateDTO interessesCreateDTO);

    InteressesJpaEntity modeltoJpaEntity(Interesses interesses);

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
