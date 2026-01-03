package com.project.roomie.ports.out;

import com.project.roomie.core.model.Match;
import com.project.roomie.infra.persistence.entity.MatchJpaEntity;

public interface MatchPortOut {

    Match save(MatchJpaEntity matchJpaEntity);
}