package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.MatchJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends JpaRepository<MatchJpaEntity, Integer> {

    @Query("""
    SELECT m
    FROM MatchJpaEntity m
    WHERE m.ofertante.id = :id_ofertante
    ORDER BY
        CASE
            WHEN m.status = 'PENDENTE' THEN 0
            ELSE 1
        END,
        m.id DESC
""")
    Page<MatchJpaEntity> findByOfertante(
            @Param("id_ofertante") Integer id_ofertante,
            Pageable pageable
    );
}