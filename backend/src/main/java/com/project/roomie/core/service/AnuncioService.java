package com.project.roomie.core.service;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.core.model.InteressesInteressados;
import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.mapper.AnuncioMapper;
import com.project.roomie.mapper.UsuarioOfertanteMapper;
import com.project.roomie.ports.in.AnuncioPortIn;
import com.project.roomie.ports.out.AnuncioPortOut;
import com.project.roomie.ports.out.UsuarioOfertantePortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnuncioService implements AnuncioPortIn {

    private final AnuncioPortOut anuncioPortOut;
    private final AnuncioMapper anuncioMapper;
    private final UsuarioOfertantePortOut usuarioOfertantePortOut;
    private final UsuarioOfertanteMapper usuarioOfertanteMapper;

    @Autowired
    public AnuncioService(AnuncioPortOut anuncioPortOut,
                                        AnuncioMapper anuncioMapper,
                                        UsuarioOfertantePortOut usuarioOfertantePortOut,
                                        UsuarioOfertanteMapper usuarioOfertanteMapper) {
        this.anuncioPortOut = anuncioPortOut;
        this.anuncioMapper = anuncioMapper;
        this.usuarioOfertantePortOut = usuarioOfertantePortOut;
        this.usuarioOfertanteMapper = usuarioOfertanteMapper;
    }

    @Override
    public Anuncio cadastrar(Anuncio anuncio, Integer id_usuario){
        Anuncio novo_anuncio = anuncioPortOut.save(anuncioMapper.ModeltoJpaEntity(anuncio));

        UsuarioOfertante usuarioOfertante = usuarioOfertantePortOut.findById(id_usuario);
        usuarioOfertante.setAnuncio(novo_anuncio);
        usuarioOfertante = usuarioOfertantePortOut.save(usuarioOfertanteMapper.ModeltoJpaEntity(usuarioOfertante));

        return novo_anuncio;
    }
}