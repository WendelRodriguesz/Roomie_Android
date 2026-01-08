package com.project.roomie.ports.in;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.dto.create.AnuncioFiltroDTO;
import com.project.roomie.dto.response.AnuncioResponseDTO;
import com.project.roomie.dto.update.AnuncioUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnuncioPortIn {
    Anuncio cadastrar(Anuncio anuncio, Integer id_usuario);
    ResponseEntity<String> uploadNovaFoto(MultipartFile file, Integer id_usuario);
    AnuncioResponseDTO atualizar(Integer id_usuario, AnuncioUpdateDTO anuncioUpdateDTO);
    AnuncioResponseDTO pausarAnuncio(Integer id_anuncio);
    AnuncioResponseDTO reativarAnuncio(Integer id_anuncio);
    List<AnuncioResponseDTO> visualizarTodos();
    List<AnuncioResponseDTO> filtrar(AnuncioFiltroDTO anuncioFiltroDTO);
    AnuncioResponseDTO visualizarPorId(Integer id);
}
