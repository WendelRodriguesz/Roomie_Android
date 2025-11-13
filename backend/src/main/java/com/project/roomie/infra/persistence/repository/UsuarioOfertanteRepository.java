package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.UsuarioInteressadoJpaEntity;
import com.project.roomie.infra.persistence.entity.UsuarioOfertanteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioOfertanteRepository extends JpaRepository<UsuarioOfertanteJpaEntity, Integer> {
}