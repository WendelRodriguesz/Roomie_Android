package com.project.roomie.ports.in;

import com.project.roomie.core.model.UsuarioOfertante;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UsuarioOfertantePortIn {

    UsuarioOfertante cadastrar(UsuarioOfertante usuarioOfertante);
    ResponseEntity<String> uploadFotoDePerfil(MultipartFile file, Integer id_usuario);
}