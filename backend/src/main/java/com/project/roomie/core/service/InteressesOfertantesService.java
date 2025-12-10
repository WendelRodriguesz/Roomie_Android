package com.project.roomie.core.service;

import com.project.roomie.core.model.InteressesInteressados;
import com.project.roomie.core.model.InteressesOfertantes;
import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.mapper.InteressesOfertantesMapper;
import com.project.roomie.mapper.UsuarioOfertanteMapper;
import com.project.roomie.ports.in.InteressesOfertantesPortIn;
import com.project.roomie.ports.out.InteressesOfertantesPortOut;
import com.project.roomie.ports.out.UsuarioOfertantePortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteressesOfertantesService implements InteressesOfertantesPortIn {

    private final InteressesOfertantesMapper interessesOfertantesMapper;
    private final InteressesOfertantesPortOut interessesOfertantesPortOut;
    private final UsuarioOfertantePortOut usuarioOfertantePortOut;
    private final UsuarioOfertanteMapper usuarioOfertanteMapper;

    @Autowired
    public InteressesOfertantesService(InteressesOfertantesPortOut interessesOfertantesPortOut,
                                       InteressesOfertantesMapper interessesOfertantesMapper,
                                       UsuarioOfertantePortOut usuarioOfertantePortOut,
                                       UsuarioOfertanteMapper usuarioOfertanteMapper) {
        this.interessesOfertantesMapper = interessesOfertantesMapper;
        this.interessesOfertantesPortOut = interessesOfertantesPortOut;
        this.usuarioOfertantePortOut = usuarioOfertantePortOut;
        this.usuarioOfertanteMapper = usuarioOfertanteMapper;
    }

    @Override
    public InteressesOfertantes cadastrarInteresses(InteressesOfertantes interessesOfertantes,
                                                    Integer id_usuario){
        InteressesOfertantes interesses = interessesOfertantesPortOut.save(interessesOfertantesMapper.ModeltoJpaEntity(interessesOfertantes));

        UsuarioOfertante usuarioOfertante = usuarioOfertantePortOut.findById(id_usuario);
        usuarioOfertante.setInteresses(interesses);
        usuarioOfertante = usuarioOfertantePortOut.save(usuarioOfertanteMapper.ModeltoJpaEntity(usuarioOfertante));

        return interesses;
    }
}
