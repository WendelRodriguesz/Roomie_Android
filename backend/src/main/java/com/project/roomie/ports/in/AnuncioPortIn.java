package com.project.roomie.ports.in;

import com.project.roomie.core.model.Anuncio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AnuncioPortIn {
    Anuncio cadastrar(Anuncio anuncio, Integer id_usuario);
    ResponseEntity<String> uploadNovaFoto(MultipartFile file, Integer id_usuario);
}