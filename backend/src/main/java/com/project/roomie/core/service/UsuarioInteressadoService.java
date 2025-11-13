package com.project.roomie.core.service;

import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.mapper.UsuarioInteressadoMapper;
import com.project.roomie.ports.in.UsuarioInteressadoPortIn;
import com.project.roomie.ports.out.UsuarioInteressadoPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioInteressadoService implements UsuarioInteressadoPortIn {

    private final UsuarioInteressadoPortOut usuarioInteressadoPortOut;
    private final UsuarioInteressadoMapper usuarioInteressadoMapper;

    @Autowired
    public UsuarioInteressadoService(UsuarioInteressadoPortOut usuarioInteressadoPortOut,
                                     UsuarioInteressadoMapper usuarioInteressadoMapper){
        this.usuarioInteressadoPortOut = usuarioInteressadoPortOut;
        this.usuarioInteressadoMapper = usuarioInteressadoMapper;
    }

    @Override
    public UsuarioInteressado cadastrar(UsuarioInteressado usuarioInteressado){



        return usuarioInteressadoPortOut.save(usuarioInteressadoMapper.ModeltoJpaEntity(usuarioInteressado));
    }
}