package com.project.roomie.mapper;

import com.project.roomie.core.model.Match;
import com.project.roomie.dto.response.MatchResponseDTO;
import com.project.roomie.infra.persistence.entity.MatchJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UsuarioOfertanteMapper.class, UsuarioInteressadoMapper.class })
public interface MatchMapper {

    @Mapping(source = "interessado", target = "interessado", qualifiedByName = "ModeltoJpaEntity")
    @Mapping(source = "ofertante", target = "ofertante", qualifiedByName = "ModeltoJpaEntity")
    MatchJpaEntity ModeltoJpaEntity(Match match);

    @Mapping(source = "interessado", target = "interessado", qualifiedByName = "JpaEntitytoModel")
    @Mapping(source = "ofertante", target = "ofertante", qualifiedByName = "JpaEntitytoModel")
    Match JpaEntitytoModel(MatchJpaEntity matchJpaEntity);

    @Mapping(source = "interessado", target = "interessado", qualifiedByName = "ModeltoResponseDTO")
    @Mapping(source = "ofertante", target = "ofertante", qualifiedByName = "ModeltoResponseDTO")
    MatchResponseDTO ModeltoResponseDTO(Match match);
}