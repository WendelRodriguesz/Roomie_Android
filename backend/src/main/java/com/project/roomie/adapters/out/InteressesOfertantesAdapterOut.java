package com.project.roomie.adapters.out;

import com.project.roomie.core.model.InteressesOfertantes;
import com.project.roomie.infra.persistence.entity.InteressesOfertantesJpaEntity;
import com.project.roomie.infra.persistence.repository.InteressesOfertantesRepository;
import com.project.roomie.mapper.InteressesOfertantesMapper;
import com.project.roomie.ports.out.InteressesOfertantesPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InteressesOfertantesAdapterOut implements InteressesOfertantesPortOut {
    private final InteressesOfertantesRepository interessesOfertantesRepository;
    private final InteressesOfertantesMapper interessesOfertantesMapper;

    @Autowired
    public InteressesOfertantesAdapterOut(InteressesOfertantesRepository interessesOfertantesRepository,
                                          InteressesOfertantesMapper interessesOfertantesMapper) {
        this.interessesOfertantesRepository = interessesOfertantesRepository;
        this.interessesOfertantesMapper = interessesOfertantesMapper;
    }

    @Override
    public InteressesOfertantes save(InteressesOfertantes interessesOfertantes) {
        InteressesOfertantesJpaEntity entity =
                interessesOfertantesMapper.ModeltoJpaEntity(interessesOfertantes);

        InteressesOfertantesJpaEntity salvo =
                interessesOfertantesRepository.save(entity);

        return interessesOfertantesMapper.JpaEntitytoModel(salvo);
    }

    @Override
    public InteressesOfertantes findById(Integer id) {
        InteressesOfertantesJpaEntity interessesOfertantesJpaEntity = interessesOfertantesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interesse não encontrado"));

        return interessesOfertantesMapper.JpaEntitytoModel(interessesOfertantesJpaEntity);
    }
}
