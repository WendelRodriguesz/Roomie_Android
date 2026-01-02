package com.project.roomie.mapper;

import com.project.roomie.core.model.InteressesOfertantes;
import com.project.roomie.dto.create.InteressesOfertantesCreateDTO;
import com.project.roomie.dto.response.InteressesOfertantesResponseDTO;
import com.project.roomie.infra.persistence.entity.InteressesOfertantesJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface InteressesOfertantesMapper {

    @Named("JpaEntitytoModel")
    InteressesOfertantes JpaEntitytoModel(InteressesOfertantesJpaEntity interessesOfertantesJpaEntity);

    @Named("ModeltoResponseDTO")
    InteressesOfertantesResponseDTO ModeltoResponseDTO(InteressesOfertantes interessesOfertantes);

    @Named("CreateDTOtoModel")
    InteressesOfertantes CreateDTOtoModel(InteressesOfertantesCreateDTO interessesOfertantesCreateDTO);

    @Named("ModeltoJpaEntity")
    InteressesOfertantesJpaEntity ModeltoJpaEntity(InteressesOfertantes interessesOfertantes);
}