package com.project.roomie.infra.persistence.repository;

import com.project.roomie.core.model.Interesses;
import com.project.roomie.infra.persistence.entity.InteressesJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteressesRepository extends JpaRepository<InteressesJpaEntity, Integer> {
}
