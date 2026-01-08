package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.InteressesInteressadosJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteressesInteressadosRepository extends JpaRepository<InteressesInteressadosJpaEntity, Integer> {
}
