package com.project.roomie.mapper;

import com.project.roomie.core.model.Chat;
import com.project.roomie.infra.persistence.entity.ChatJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Named("JpaEntitytoModel")
    Chat JpaEntitytoModel(ChatJpaEntity chatJpaEntity);

//    @Named("ModeltoResponseDTO")
//    ChatResponseDTO ModeltoResponseDTO(Chat chat);

    @Named("ModeltoJpaEntity")
    ChatJpaEntity ModeltoJpaEntity(Chat chat);
}