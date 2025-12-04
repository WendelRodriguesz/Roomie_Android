package com.project.roomie.core.service;

import com.project.roomie.core.model.InteressesInteressados;
import com.project.roomie.mapper.InteressesInteressadosMapper;
import com.project.roomie.ports.in.InteressesInteressadosPortIn;
import com.project.roomie.ports.out.InteressesInteressadosPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteresseInteressadosService implements InteressesInteressadosPortIn {

    private final InteressesInteressadosPortOut interessesInteressadosPortOut;
    private final InteressesInteressadosMapper interessesInteressadosMapper;

    @Autowired
    public InteresseInteressadosService(InteressesInteressadosPortOut interessesInteressadosPortOut,
                                        InteressesInteressadosMapper interessesInteressadosMapper) {
        this.interessesInteressadosPortOut = interessesInteressadosPortOut;
        this.interessesInteressadosMapper = interessesInteressadosMapper;
    }

    @Override
    public InteressesInteressados cadastrarInteresses(InteressesInteressados interessesInteressados) {
       return interessesInteressadosPortOut.save(interessesInteressadosMapper.modeltoJpaEntity(interessesInteressados));
    }
}
