package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.UsuarioOfertanteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioOfertanteRepository extends JpaRepository<UsuarioOfertanteJpaEntity, Integer> {

    @Query("""
    SELECT u
    FROM UsuarioOfertanteJpaEntity u
    WHERE u.role = 'OFERTANTE'
      AND u.anuncio IS NOT NULL
      AND u.interesses IS NOT NULL
      AND NOT EXISTS (
          SELECT 1
          FROM MatchJpaEntity m
          WHERE m.interessado.id = :id_usuario_interessado
            AND m.ofertante.id = u.id
      )
""")
    List<UsuarioOfertanteJpaEntity> buscarCandidatosMatch(
            @Param("id_usuario_interessado") Integer id_usuario_interessado
    );
}