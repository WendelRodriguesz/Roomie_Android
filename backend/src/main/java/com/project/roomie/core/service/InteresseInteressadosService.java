package com.project.roomie.core.service;

import com.project.roomie.core.model.InteressesInteressados;
import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.mapper.InteressesInteressadosMapper;
import com.project.roomie.mapper.UsuarioInteressadoMapper;
import com.project.roomie.ports.in.InteressesInteressadosPortIn;
import com.project.roomie.ports.out.InteressesInteressadosPortOut;
import com.project.roomie.ports.out.UsuarioInteressadoPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteresseInteressadosService implements InteressesInteressadosPortIn {

    private final InteressesInteressadosPortOut interessesInteressadosPortOut;
    private final InteressesInteressadosMapper interessesInteressadosMapper;
    private final UsuarioInteressadoPortOut usuarioInteressadoPortOut;
    private final UsuarioInteressadoMapper usuarioInteressadoMapper;

    @Autowired
    public InteresseInteressadosService(InteressesInteressadosPortOut interessesInteressadosPortOut,
                                        InteressesInteressadosMapper interessesInteressadosMapper,
                                        UsuarioInteressadoPortOut usuarioInteressadoPortOut,
                                        UsuarioInteressadoMapper usuarioInteressadoMapper) {
        this.interessesInteressadosPortOut = interessesInteressadosPortOut;
        this.interessesInteressadosMapper = interessesInteressadosMapper;
        this.usuarioInteressadoPortOut = usuarioInteressadoPortOut;
        this.usuarioInteressadoMapper = usuarioInteressadoMapper;
    }

    @Override
    public InteressesInteressados cadastrarInteresses(InteressesInteressados interessesInteressados,
                                                      Integer id_usuario) {

        InteressesInteressados interesses = interessesInteressadosPortOut.save(interessesInteressadosMapper.ModeltoJpaEntity(interessesInteressados));

        UsuarioInteressado usuarioInteressado = usuarioInteressadoPortOut.findById(id_usuario);
        usuarioInteressado.setInteresses(interesses);
        usuarioInteressado = usuarioInteressadoPortOut.save(usuarioInteressadoMapper.ModeltoJpaEntity(usuarioInteressado));

        return interesses;
    }
}