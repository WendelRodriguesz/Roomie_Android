package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.AnuncioJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.StatusAnuncioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AnuncioRepository extends JpaRepository<AnuncioJpaEntity, Integer>,
                                            JpaSpecificationExecutor<AnuncioJpaEntity> {
    List<AnuncioJpaEntity> findByStatusAnuncio(StatusAnuncioJpaEntity statusAnuncio);

}