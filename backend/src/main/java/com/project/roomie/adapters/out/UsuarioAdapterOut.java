package com.project.roomie.adapters.out;

import com.project.roomie.core.model.Usuario;
import com.project.roomie.infra.persistence.repository.UsuarioRepository;
import com.project.roomie.mapper.UsuarioMapper;
import com.project.roomie.ports.out.UsuarioPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioAdapterOut implements UsuarioPortOut {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioAdapterOut(UsuarioRepository usuarioRepository,
                             UsuarioMapper usuarioMapper){
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Usuario findByEmail(String email){
        return usuarioMapper.JpaEntitytoModel(usuarioRepository.findByEmail(email));
    }
}