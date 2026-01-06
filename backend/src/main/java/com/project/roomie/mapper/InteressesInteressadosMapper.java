package com.project.roomie.mapper;

import com.project.roomie.core.model.InteressesInteressados;
import com.project.roomie.dto.create.InteressesInteressadosCreateDTO;
import com.project.roomie.dto.response.InteressesInteressadosResponseDTO;
import com.project.roomie.dto.update.InteressesInteressadosUpdateDTO;
import com.project.roomie.infra.persistence.entity.InteressesInteressadosJpaEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface InteressesInteressadosMapper {

    @Named("JpaEntitytoModel")
    InteressesInteressados JpaEntitytoModel(InteressesInteressadosJpaEntity interessesInteressadosJpaEntity);

    @Named("ModeltoResponseDTO")
    InteressesInteressadosResponseDTO ModeltoResponseDTO(InteressesInteressados interessesInteressados);

    @Named("ModeltoResponseDTO")
    InteressesInteressados CreateDTOtoModel(InteressesInteressadosCreateDTO interessesInteressadosCreateDTO);

    @Named("ModeltoJpaEntity")
    InteressesInteressadosJpaEntity ModeltoJpaEntity(InteressesInteressados interessesInteressados);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInteresseFromDto(
            InteressesInteressadosUpdateDTO dto,
            @MappingTarget InteressesInteressados interessesInteressados
    );
}