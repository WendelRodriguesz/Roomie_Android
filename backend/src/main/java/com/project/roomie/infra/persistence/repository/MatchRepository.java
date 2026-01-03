package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.MatchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchJpaEntity, Integer> {
}