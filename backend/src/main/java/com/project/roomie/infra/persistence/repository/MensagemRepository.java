package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.MensagemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<MensagemJpaEntity, Integer> {

    @Query("""
        SELECT m
        FROM MensagemJpaEntity m
        WHERE m.id_chat = :id_chat
        ORDER BY m.enviada_em DESC
    """)
    public List<MensagemJpaEntity> findAllById_chat(@Param("id_chat") Integer id_chat);
}