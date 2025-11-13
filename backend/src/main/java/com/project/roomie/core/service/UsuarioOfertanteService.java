package com.project.roomie.core.service;

import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.mapper.UsuarioOfertanteMapper;
import com.project.roomie.ports.in.UsuarioOfertantePortIn;
import com.project.roomie.ports.out.UsuarioOfertantePortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioOfertanteService implements UsuarioOfertantePortIn {

    private final UsuarioOfertantePortOut usuarioOfertantePortOut;
    private final UsuarioOfertanteMapper usuarioOfertanteMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioOfertanteService(UsuarioOfertantePortOut usuarioOfertantePortOut,
                                   UsuarioOfertanteMapper usuarioOfertanteMapper,
                                   PasswordEncoder passwordEncoder){
        this.usuarioOfertantePortOut = usuarioOfertantePortOut;
        this.usuarioOfertanteMapper = usuarioOfertanteMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioOfertante cadastrar(UsuarioOfertante usuarioOfertante){

        usuarioOfertante.setSenha(passwordEncoder.encode(usuarioOfertante.getSenha()));
        return usuarioOfertantePortOut.save(usuarioOfertanteMapper.ModeltoJpaEntity(usuarioOfertante));
    }
}