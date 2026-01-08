package com.project.roomie.mapper;

import com.project.roomie.core.model.InteressesInteressados;
import com.project.roomie.dto.create.InteressesInteressadosCreateDTO;
import com.project.roomie.dto.response.InteressesInteressadosResponseDTO;
import com.project.roomie.dto.update.InteressesInteressadosUpdateDTO;
import com.project.roomie.infra.persistence.entity.InteressesInteressadosJpaEntity;
import org.mapstruct.*;


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
    @Mapping(source = "orcamentoMin", target = "orcamento_min")
    @Mapping(source = "orcamentoMax", target = "orcamento_max")
    void updateInteresseFromDto(
            InteressesInteressadosUpdateDTO dto,
            @MappingTarget InteressesInteressados interessesInteressados
    );
}