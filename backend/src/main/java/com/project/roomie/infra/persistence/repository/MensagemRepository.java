package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.MensagemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemRepository extends JpaRepository<MensagemJpaEntity, Integer> {
}