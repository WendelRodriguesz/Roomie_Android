package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.AnuncioJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.StatusAnuncioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnuncioRepository extends JpaRepository<AnuncioJpaEntity, Integer>,
                                            JpaSpecificationExecutor<AnuncioJpaEntity> {
    List<AnuncioJpaEntity> findByStatusAnuncio(StatusAnuncioJpaEntity statusAnuncio);

    @Query("SELECT a FROM AnuncioJpaEntity a LEFT JOIN FETCH a.ofertante WHERE a.id = :id")
    Optional<AnuncioJpaEntity> findByIdWithOfertante(Integer id);

}