package com.project.roomie.ports.in;

import com.project.roomie.core.model.UsuarioInteressado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UsuarioInteressadoPortIn {

    UsuarioInteressado cadastrar(UsuarioInteressado usuarioInteressado);
    ResponseEntity<String> uploadFotoDePerfil(MultipartFile file, Integer idUsuario);
}