package com.project.roomie.mapper;

import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.dto.create.UsuarioInteressadoCreateDTO;
import com.project.roomie.dto.response.UsuarioInteressadoResponseDTO;
import com.project.roomie.dto.update.UsuarioInteressadoUpdateDTO;
import com.project.roomie.infra.persistence.entity.UsuarioInteressadoJpaEntity;
import com.project.roomie.util.DateFormatter;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { DateFormatter.class, InteressesInteressadosMapper.class })
public interface UsuarioInteressadoMapper {

    @Named("CreateDTOtoModel")
    @Mapping(source = "data_de_nascimento", target = "data_de_nascimento", qualifiedByName = "toLocalDate")
    UsuarioInteressado CreateDTOtoModel(UsuarioInteressadoCreateDTO usuarioInteressadoCreateDTO);

    @Named("ModeltoJpaEntity")
    @Mapping(source = "interesses", target = "interesses", qualifiedByName = "ModeltoJpaEntity")
    UsuarioInteressadoJpaEntity ModeltoJpaEntity(UsuarioInteressado usuarioInteressado);

    @Named("JpaEntitytoModel")
    @Mapping(source = "interesses", target = "interesses", qualifiedByName = "JpaEntitytoModel")
    UsuarioInteressado JpaEntitytoModel(UsuarioInteressadoJpaEntity usuarioInteressadoJpaEntity);

    @Named("ModeltoResponseDTO")
    @Mapping(source = "data_de_nascimento", target = "data_de_nascimento", qualifiedByName = "toStringDate")
    @Mapping(source = "interesses", target = "interesses", qualifiedByName = "ModeltoResponseDTO")
    UsuarioInteressadoResponseDTO ModeltoResponseDTO(UsuarioInteressado usuarioInteressado);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUsuarioFromDto(
            UsuarioInteressadoUpdateDTO dto,
            @MappingTarget UsuarioInteressado usuarioInteressado
    );
}