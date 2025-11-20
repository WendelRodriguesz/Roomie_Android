package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioJpaEntity, Integer> {

    UsuarioJpaEntity findByEmail(String email);
}