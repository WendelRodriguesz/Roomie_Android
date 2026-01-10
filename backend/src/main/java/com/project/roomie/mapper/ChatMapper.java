package com.project.roomie.mapper;

import com.project.roomie.core.model.Chat;
import com.project.roomie.dto.response.ChatResponseDTO;
import com.project.roomie.infra.persistence.entity.ChatJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Named("JpaEntitytoModel")
    Chat JpaEntitytoModel(ChatJpaEntity chatJpaEntity);

    @Named("JpaEntitytoModel")
    List<Chat> JpaEntitytoModel(List<ChatJpaEntity> chatJpaEntity);

    @Named("ModeltoResponseDTO")
    ChatResponseDTO ModeltoResponseDTO(Chat chat);

    @Named("ModeltoResponseDTO")
    List<ChatResponseDTO> ModeltoResponseDTO(List<Chat> chat);

    @Named("ModeltoJpaEntity")
    ChatJpaEntity ModeltoJpaEntity(Chat chat);

    @Named("ModeltoJpaEntity")
    List<ChatJpaEntity> ModeltoJpaEntity(List<Chat> chat);
}