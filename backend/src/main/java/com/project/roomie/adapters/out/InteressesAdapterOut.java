package com.project.roomie.adapters.out;

import com.project.roomie.adapters.in.InteressesAdapterIn;
import com.project.roomie.core.model.Interesses;
import com.project.roomie.infra.persistence.entity.InteressesJpaEntity;
import com.project.roomie.infra.persistence.repository.InteressesRepository;
import com.project.roomie.mapper.InteressesMapper;
import com.project.roomie.ports.out.InteressesPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InteressesAdapterOut implements InteressesPortOut {
    private final InteressesRepository interessesRepository;
    private final InteressesMapper interessesMapper;

    @Autowired
    public InteressesAdapterOut(InteressesRepository interessesRepository, InteressesMapper interessesMapper) {
        this.interessesRepository = interessesRepository;
        this.interessesMapper = interessesMapper;
    }

    @Override
    public Interesses save(InteressesJpaEntity interessesJpaEntity){
        return interessesMapper.jpaEntitytoModel(interessesRepository.save(interessesJpaEntity));
    }
}
