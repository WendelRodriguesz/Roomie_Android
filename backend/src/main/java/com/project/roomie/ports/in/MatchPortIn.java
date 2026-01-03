package com.project.roomie.ports.in;

import com.project.roomie.core.model.Match;
import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.core.model.UsuarioOfertante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchPortIn {

    Page<UsuarioOfertante> buscarMatches(Integer id_usuario, Pageable pageable);
    Match enviarLike(Integer id_usuario_interessado, Integer id_usuario_ofertante);
    Match aceitarMatch(Integer id_match);
}