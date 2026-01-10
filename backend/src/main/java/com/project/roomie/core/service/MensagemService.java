package com.project.roomie.core.service;

import com.project.roomie.core.model.Mensagem;
import com.project.roomie.mapper.MensagemMapper;
import com.project.roomie.ports.in.MensagemPortIn;
import com.project.roomie.ports.out.MensagemPortOut;
import com.project.roomie.ports.out.UsuarioPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MensagemService implements MensagemPortIn {

    private final MensagemPortOut mensagemPortOut;
    private final MensagemMapper mensagemMapper;

    @Autowired
    public MensagemService(MensagemPortOut mensagemPortOut,
                       UsuarioPortOut usuarioPortOut,
                       MensagemMapper mensagemMapper){
        this.mensagemPortOut = mensagemPortOut;
        this.mensagemMapper = mensagemMapper;
    }

    @Override
    public Mensagem cadastrarMensagem(Mensagem mensagem){
        mensagem.setEnviada_em(LocalDateTime.now());
        return mensagemPortOut.save(mensagem);
    }
}