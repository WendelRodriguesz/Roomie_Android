package com.project.roomie.adapters.out;

import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.infra.persistence.entity.UsuarioOfertanteJpaEntity;
import com.project.roomie.infra.persistence.repository.UsuarioOfertanteRepository;
import com.project.roomie.mapper.UsuarioOfertanteMapper;
import com.project.roomie.ports.out.UsuarioOfertantePortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioOfertanteAdapterOut implements UsuarioOfertantePortOut {

    private final UsuarioOfertanteRepository usuarioOfertanteRepository;
    private final UsuarioOfertanteMapper usuarioOfertanteMapper;

    @Autowired
    public UsuarioOfertanteAdapterOut(UsuarioOfertanteRepository usuarioOfertanteRepository,
                                        UsuarioOfertanteMapper usuarioOfertanteMapper){
        this.usuarioOfertanteRepository = usuarioOfertanteRepository;
        this.usuarioOfertanteMapper = usuarioOfertanteMapper;
    }

    @Override
    public UsuarioOfertante save(UsuarioOfertante usuarioOfertante){
        UsuarioOfertanteJpaEntity entity =
                usuarioOfertanteMapper.ModeltoJpaEntity(usuarioOfertante);

        UsuarioOfertanteJpaEntity salvo =
                usuarioOfertanteRepository.save(entity);

        return usuarioOfertanteMapper.JpaEntitytoModel(salvo);
    }

    @Override
    public UsuarioOfertante findById(Integer id){
        UsuarioOfertanteJpaEntity usuarioOfertanteJpaEntity = usuarioOfertanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return usuarioOfertanteMapper.JpaEntitytoModel(usuarioOfertanteJpaEntity);
    }

    @Override
    public List<UsuarioOfertante> buscarCandidatosMatch(Integer id_usuario_interessado){
        return usuarioOfertanteMapper.JpaEntitytoModel(usuarioOfertanteRepository.buscarCandidatosMatch(id_usuario_interessado));
    }
}