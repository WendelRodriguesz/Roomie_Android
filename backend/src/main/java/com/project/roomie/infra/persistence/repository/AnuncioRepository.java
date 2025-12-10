package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.AnuncioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnuncioRepository extends JpaRepository<AnuncioJpaEntity, Integer> {
}