package com.project.roomie.adapters.out;

import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.infra.persistence.entity.UsuarioInteressadoJpaEntity;
import com.project.roomie.infra.persistence.repository.UsuarioInteressadoRepository;
import com.project.roomie.mapper.UsuarioInteressadoMapper;
import com.project.roomie.ports.out.UsuarioInteressadoPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioInteressadoAdapterOut implements UsuarioInteressadoPortOut {

    private final UsuarioInteressadoRepository usuarioInteressadoRepository;
    private final UsuarioInteressadoMapper usuarioInteressadoMapper;

    @Autowired
    public UsuarioInteressadoAdapterOut(UsuarioInteressadoRepository usuarioInteressadoRepository,
                                        UsuarioInteressadoMapper usuarioInteressadoMapper){
        this.usuarioInteressadoRepository = usuarioInteressadoRepository;
        this.usuarioInteressadoMapper = usuarioInteressadoMapper;
    }

    @Override
    public UsuarioInteressado save(UsuarioInteressadoJpaEntity usuarioInteressadoJpaEntity){
        return usuarioInteressadoMapper.JpaEntitytoModel(usuarioInteressadoRepository.save(usuarioInteressadoJpaEntity));
    }

    @Override
    public UsuarioInteressado findById(Integer id){
        UsuarioInteressadoJpaEntity usuarioInteressadoJpaEntity = usuarioInteressadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return usuarioInteressadoMapper.JpaEntitytoModel(usuarioInteressadoJpaEntity);
    }
}