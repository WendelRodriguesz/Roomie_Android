package com.project.roomie.mapper;

import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.dto.create.UsuarioInteressadoCreateDTO;
import com.project.roomie.dto.response.UsuarioInteressadoResponseDTO;
import com.project.roomie.infra.persistence.entity.UsuarioInteressadoJpaEntity;
import com.project.roomie.util.DateFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateFormatter.class })
public interface UsuarioInteressadoMapper {

    @Mapping(source = "data_de_nascimento", target = "data_de_nascimento", qualifiedByName = "toLocalDate")
    UsuarioInteressado CreateDTOtoModel(UsuarioInteressadoCreateDTO usuarioInteressadoCreateDTO);

    UsuarioInteressadoJpaEntity ModeltoJpaEntity(UsuarioInteressado usuarioInteressado);

    UsuarioInteressado JpaEntitytoModel(UsuarioInteressadoJpaEntity usuarioInteressadoJpaEntity);

    @Mapping(source = "data_de_nascimento", target = "data_de_nascimento", qualifiedByName = "toStringDate")
    UsuarioInteressadoResponseDTO ModeltoResponseDTO(UsuarioInteressado usuarioInteressado);
}