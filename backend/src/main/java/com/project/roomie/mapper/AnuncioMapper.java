package com.project.roomie.mapper;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.dto.create.AnuncioCreateDTO;
import com.project.roomie.dto.response.AnuncioResponseDTO;
import com.project.roomie.infra.persistence.entity.AnuncioJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AnuncioMapper {

    @Named("JpaEntitytoModel")
    Anuncio JpaEntitytoModel(AnuncioJpaEntity anunciosJpaEntity);

    @Named("ModeltoResponseDTO")
    AnuncioResponseDTO ModeltoResponseDTO(Anuncio anuncios);

    @Named("ModeltoResponseDTO")
    Anuncio CreateDTOtoModel(AnuncioCreateDTO anunciosCreateDTO);

    @Named("ModeltoJpaEntity")
    AnuncioJpaEntity ModeltoJpaEntity(Anuncio anuncios);
}