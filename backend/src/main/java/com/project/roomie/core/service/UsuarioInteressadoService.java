package com.project.roomie.core.service;

import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.mapper.UsuarioInteressadoMapper;
import com.project.roomie.ports.in.UsuarioInteressadoPortIn;
import com.project.roomie.ports.out.UsuarioInteressadoPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioInteressadoService implements UsuarioInteressadoPortIn {

    private final UsuarioInteressadoPortOut usuarioInteressadoPortOut;
    private final UsuarioInteressadoMapper usuarioInteressadoMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioInteressadoService(UsuarioInteressadoPortOut usuarioInteressadoPortOut,
                                     UsuarioInteressadoMapper usuarioInteressadoMapper,
                                     PasswordEncoder passwordEncoder){
        this.usuarioInteressadoPortOut = usuarioInteressadoPortOut;
        this.usuarioInteressadoMapper = usuarioInteressadoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioInteressado cadastrar(UsuarioInteressado usuarioInteressado){

        usuarioInteressado.setSenha(passwordEncoder.encode(usuarioInteressado.getSenha()));
        return usuarioInteressadoPortOut.save(usuarioInteressadoMapper.ModeltoJpaEntity(usuarioInteressado));
    }
}