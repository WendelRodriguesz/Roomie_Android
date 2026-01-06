package com.project.roomie.infra.persistence.repository;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.core.model.enums.StatusAnuncio;
import com.project.roomie.infra.persistence.entity.AnuncioJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.StatusAnuncioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnuncioRepository extends JpaRepository<AnuncioJpaEntity, Integer> {
    List<AnuncioJpaEntity> findByStatusAnuncio(StatusAnuncioJpaEntity statusAnuncio);

}