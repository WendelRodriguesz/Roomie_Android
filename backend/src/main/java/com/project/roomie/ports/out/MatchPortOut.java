package com.project.roomie.ports.out;

import com.project.roomie.core.model.Match;
import com.project.roomie.infra.persistence.entity.MatchJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchPortOut {

    Match save(MatchJpaEntity matchJpaEntity);
    Match findById(Integer id_match);
    Page<Match> findByOfertante(Integer id_ofertante, Pageable pageable);
}