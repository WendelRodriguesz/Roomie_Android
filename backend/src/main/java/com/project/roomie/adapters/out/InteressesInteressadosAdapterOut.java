package com.project.roomie.adapters.out;

import com.project.roomie.core.model.InteressesInteressados;
import com.project.roomie.infra.persistence.entity.InteressesInteressadosJpaEntity;
import com.project.roomie.infra.persistence.repository.InteressesInteressadosRepository;
import com.project.roomie.mapper.InteressesInteressadosMapper;
import com.project.roomie.ports.out.InteressesInteressadosPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InteressesInteressadosAdapterOut implements InteressesInteressadosPortOut {
    private final InteressesInteressadosRepository interessesInteressadosRepository;
    private final InteressesInteressadosMapper interessesInteressadosMapper;

    @Autowired
    public InteressesInteressadosAdapterOut(InteressesInteressadosRepository interessesInteressadosRepository, InteressesInteressadosMapper interessesInteressadosMapper) {
        this.interessesInteressadosRepository = interessesInteressadosRepository;
        this.interessesInteressadosMapper = interessesInteressadosMapper;
    }

    @Override
    public InteressesInteressados save(InteressesInteressadosJpaEntity interessesInteressadosJpaEntity){
        return interessesInteressadosMapper.JpaEntitytoModel(interessesInteressadosRepository.save(interessesInteressadosJpaEntity));
    }
}