package com.project.roomie.mapper;

import com.project.roomie.core.model.Mensagem;
import com.project.roomie.dto.create.MensagemCreateDTO;
import com.project.roomie.dto.response.MensagemResponseDTO;
import com.project.roomie.infra.persistence.entity.MensagemJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MensagemMapper {

    @Named("JpaEntitytoModel")
    Mensagem JpaEntitytoModel(MensagemJpaEntity mensagemJpaEntity);

    @Named("JpaEntitytoModel")
    List<Mensagem> JpaEntitytoModel(List<MensagemJpaEntity> mensagemJpaEntity);

    @Named("ModeltoResponseDTO")
    MensagemResponseDTO ModeltoResponseDTO(Mensagem mensagem);

    @Named("ModeltoResponseDTO")
    List<MensagemResponseDTO> ModeltoResponseDTO(List<Mensagem> mensagem);

    @Named("ModeltoJpaEntity")
    MensagemJpaEntity ModeltoJpaEntity(Mensagem mensagem);

    @Named("ModeltoJpaEntity")
    List<MensagemJpaEntity> ModeltoJpaEntity(List<Mensagem> mensagem);

    @Named("CreateDTOtoModel")
    Mensagem CreateDTOtoModel(MensagemCreateDTO mensagemCreateDTO);

    @Named("CreateDTOtoModel")
    List<Mensagem> CreateDTOtoModel(List<MensagemCreateDTO> mensagemCreateDTO);
}