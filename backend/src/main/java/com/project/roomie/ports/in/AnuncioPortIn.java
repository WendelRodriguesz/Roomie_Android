package com.project.roomie.ports.in;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.dto.response.AnuncioResponseDTO;
import com.project.roomie.dto.update.AnuncioUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AnuncioPortIn {
    Anuncio cadastrar(Anuncio anuncio, Integer id_usuario);
    ResponseEntity<String> uploadNovaFoto(MultipartFile file, Integer id_usuario);
    AnuncioResponseDTO atualizar(Integer id_usuario, AnuncioUpdateDTO anuncioUpdateDTO);
}