package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.ChatJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatJpaEntity, Integer> {

    @Query("""
        SELECT c
        FROM ChatJpaEntity c
        WHERE c.id_interessado = :id_interessado
        ORDER BY c.usado_em DESC
    """)
    List<ChatJpaEntity> findAllById_interessado(@Param("id_interessado") Integer id_interessado);

    @Query("""
        SELECT c
        FROM ChatJpaEntity c
        WHERE c.id_ofertante = :id_ofertante
        ORDER BY c.usado_em DESC
""")
    List<ChatJpaEntity> findAllById_ofertante(@Param("id_ofertante") Integer id_ofertante);
}