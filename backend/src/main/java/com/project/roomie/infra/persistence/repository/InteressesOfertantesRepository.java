package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.InteressesOfertantesJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteressesOfertantesRepository extends JpaRepository<InteressesOfertantesJpaEntity, Integer> {

}
