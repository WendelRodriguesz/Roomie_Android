package com.project.roomie.mapper;

import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.dto.create.UsuarioOfertanteCreateDTO;
import com.project.roomie.dto.response.UsuarioOfertanteResponseDTO;
import com.project.roomie.dto.update.UsuarioOfertanteUpdateDTO;
import com.project.roomie.infra.persistence.entity.UsuarioOfertanteJpaEntity;
import com.project.roomie.util.DateFormatter;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = { DateFormatter.class })
public interface UsuarioOfertanteMapper {

    @Mapping(source = "data_de_nascimento", target = "data_de_nascimento", qualifiedByName = "toLocalDate")
    UsuarioOfertante CreateDTOtoModel(UsuarioOfertanteCreateDTO usuarioOfertanteCreateDTO);

    UsuarioOfertanteJpaEntity ModeltoJpaEntity(UsuarioOfertante usuarioOfertante);

    UsuarioOfertante JpaEntitytoModel(UsuarioOfertanteJpaEntity usuarioOfertanteJpaEntity);

    List<UsuarioOfertante> JpaEntitytoModel(List<UsuarioOfertanteJpaEntity> usuarios);

    @Mapping(source = "data_de_nascimento", target = "data_de_nascimento", qualifiedByName = "toStringDate")
    UsuarioOfertanteResponseDTO ModeltoResponseDTO(UsuarioOfertante usuarioOfertante);

    @Mapping(source = "data_de_nascimento", target = "data_de_nascimento", qualifiedByName = "toStringDate")
    List<UsuarioOfertanteResponseDTO> ModeltoResponseDTO(List<UsuarioOfertante> usuarios);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUsuarioFromDto(
            UsuarioOfertanteUpdateDTO dto,
            @MappingTarget UsuarioOfertante usuarioOfertante
    );
}