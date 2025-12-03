package com.project.roomie.core.service;

import com.project.roomie.core.model.Interesses;
import com.project.roomie.mapper.InteressesMapper;
import com.project.roomie.ports.in.InteressesPortIn;
import com.project.roomie.ports.out.InteressesPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteresseService implements InteressesPortIn {

    private final InteressesPortOut interessesPortOut;
    private final InteressesMapper interessesMapper;

    @Autowired
    public InteresseService(InteressesPortOut interessesPortOut,
                             InteressesMapper interessesMapper) {
        this.interessesPortOut = interessesPortOut;
        this.interessesMapper = interessesMapper;
    }

    @Override
    public Interesses cadastrarInteresses(Interesses interesses) {
       return interessesPortOut.save(interessesMapper.modeltoJpaEntity(interesses));
    }
}
