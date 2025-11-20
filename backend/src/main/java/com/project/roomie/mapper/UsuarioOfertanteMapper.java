package com.project.roomie.mapper;

import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.dto.create.UsuarioOfertanteCreateDTO;
import com.project.roomie.dto.response.UsuarioOfertanteResponseDTO;
import com.project.roomie.infra.persistence.entity.UsuarioOfertanteJpaEntity;
import com.project.roomie.util.DateFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateFormatter.class })
public interface UsuarioOfertanteMapper {

    @Mapping(source = "data_de_nascimento", target = "data_de_nascimento", qualifiedByName = "toLocalDate")
    UsuarioOfertante CreateDTOtoModel(UsuarioOfertanteCreateDTO usuarioOfertanteCreateDTO);

    UsuarioOfertanteJpaEntity ModeltoJpaEntity(UsuarioOfertante usuarioOfertante);

    UsuarioOfertante JpaEntitytoModel(UsuarioOfertanteJpaEntity usuarioOfertanteJpaEntity);

    @Mapping(source = "data_de_nascimento", target = "data_de_nascimento", qualifiedByName = "toStringDate")
    UsuarioOfertanteResponseDTO ModeltoResponseDTO(UsuarioOfertante usuarioOfertante);
}