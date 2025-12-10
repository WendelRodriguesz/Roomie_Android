package com.project.roomie.mapper;

import com.project.roomie.core.model.InteressesOfertantes;
import com.project.roomie.core.model.enums.FrequenciaFestas;
import com.project.roomie.core.model.enums.HabitosLimpeza;
import com.project.roomie.core.model.enums.HorarioSono;
import com.project.roomie.dto.create.InteressesOfertantesCreateDTO;
import com.project.roomie.dto.response.InteressesOfertantesResponseDTO;
import com.project.roomie.infra.persistence.entity.InteressesOfertantesJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.FrequenciaFestasJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.HabitosLimpezaJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.HorarioSonoJpaEntity;
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

    // ----------------------------------------------
    // Enum converters
    // ----------------------------------------------

    // FrequenciaFestas
    default FrequenciaFestasJpaEntity map(FrequenciaFestas value) {
        return value == null ? null : FrequenciaFestasJpaEntity.valueOf(value.name());
    }
    default FrequenciaFestas map(FrequenciaFestasJpaEntity value) {
        return value == null ? null : FrequenciaFestas.valueOf(value.name());
    }

    // HabitosLimpeza
    default HabitosLimpezaJpaEntity map(HabitosLimpeza value) {
        return value == null ? null : HabitosLimpezaJpaEntity.valueOf(value.name());
    }
    default HabitosLimpeza map(HabitosLimpezaJpaEntity value) {
        return value == null ? null : HabitosLimpeza.valueOf(value.name());
    }

    // HorarioSono
    default HorarioSonoJpaEntity map(HorarioSono value) {
        return value == null ? null : HorarioSonoJpaEntity.valueOf(value.name());
    }
    default HorarioSono map(HorarioSonoJpaEntity value) {
        return value == null ? null : HorarioSono.valueOf(value.name());
    }
}
