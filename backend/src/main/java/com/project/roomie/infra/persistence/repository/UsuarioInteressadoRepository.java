package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.UsuarioInteressadoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioInteressadoRepository extends JpaRepository<UsuarioInteressadoJpaEntity, Integer> {
}