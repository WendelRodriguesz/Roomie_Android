package com.project.roomie.adapters.out;

import com.project.roomie.core.model.Mensagem;
import com.project.roomie.infra.persistence.repository.MensagemRepository;
import com.project.roomie.mapper.MensagemMapper;
import com.project.roomie.ports.out.MensagemPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MensagemAdapterOut implements MensagemPortOut {

    private final MensagemRepository mensagemRepository;
    private final MensagemMapper mensagemMapper;

    @Autowired
    public MensagemAdapterOut(MensagemRepository mensagemRepository,
                          MensagemMapper mensagemMapper){
        this.mensagemRepository = mensagemRepository;
        this.mensagemMapper = mensagemMapper;
    }

    @Override
    public Mensagem save(Mensagem mensagem){
        return mensagemMapper.JpaEntitytoModel(mensagemRepository.save(mensagemMapper.ModeltoJpaEntity(mensagem)));
    }

    @Override
    public List<Mensagem> findAllById_chat(Integer id_chat){
        return mensagemMapper.JpaEntitytoModel(mensagemRepository.findAllById_chat(id_chat));
    }
}