package com.project.roomie.adapters.out;

import com.project.roomie.core.model.Match;
import com.project.roomie.infra.persistence.entity.MatchJpaEntity;
import com.project.roomie.infra.persistence.repository.MatchRepository;
import com.project.roomie.mapper.MatchMapper;
import com.project.roomie.ports.out.MatchPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Match findById(Integer id_match){
        MatchJpaEntity matchJpaEntity = matchRepository.findById(id_match)
                .orElseThrow(() -> new RuntimeException("Match não encontrado"));

        return matchMapper.JpaEntitytoModel(matchJpaEntity);
    }

    @Override
    public Page<Match> findByOfertante(Integer idOfertante, Pageable pageable) {

        Page<MatchJpaEntity> pageEntity = matchRepository.findByOfertante(idOfertante, pageable);
        return pageEntity.map(matchMapper::JpaEntitytoModel);
    }
}