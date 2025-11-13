package com.project.roomie.core.service;

import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.mapper.UsuarioOfertanteMapper;
import com.project.roomie.ports.in.UsuarioOfertantePortIn;
import com.project.roomie.ports.out.UsuarioOfertantePortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioOfertanteService implements UsuarioOfertantePortIn {

    private final UsuarioOfertantePortOut usuarioOfertantePortOut;
    private final UsuarioOfertanteMapper usuarioOfertanteMapper;

    @Autowired
    public UsuarioOfertanteService(UsuarioOfertantePortOut usuarioOfertantePortOut,
                                     UsuarioOfertanteMapper usuarioOfertanteMapper){
        this.usuarioOfertantePortOut = usuarioOfertantePortOut;
        this.usuarioOfertanteMapper = usuarioOfertanteMapper;
    }

    @Override
    public UsuarioOfertante cadastrar(UsuarioOfertante usuarioOfertante){



        return usuarioOfertantePortOut.save(usuarioOfertanteMapper.ModeltoJpaEntity(usuarioOfertante));
    }
}