package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.ChatJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatJpaEntity, Integer> {
}