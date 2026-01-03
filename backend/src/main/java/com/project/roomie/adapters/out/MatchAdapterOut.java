package com.project.roomie.adapters.out;

import com.project.roomie.core.model.Match;
import com.project.roomie.infra.persistence.entity.MatchJpaEntity;
import com.project.roomie.infra.persistence.repository.MatchRepository;
import com.project.roomie.mapper.MatchMapper;
import com.project.roomie.ports.out.MatchPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MatchAdapterOut implements MatchPortOut {

    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    @Autowired
    public MatchAdapterOut(MatchRepository matchRepository,
                                          MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    @Override
    public Match save(MatchJpaEntity matchJpaEntity) {
        return matchMapper.JpaEntitytoModel(matchRepository.save(matchJpaEntity));
    }
}