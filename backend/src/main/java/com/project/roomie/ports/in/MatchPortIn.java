package com.project.roomie.ports.in;

import com.project.roomie.core.model.UsuarioOfertante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchPortIn {

    Page<UsuarioOfertante> buscarMatches(Integer id_usuario, Pageable pageable);
}