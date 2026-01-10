package com.project.roomie.ports.in;

import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.dto.response.FirebaseTokenResponseDTO;
import com.project.roomie.dto.response.UsuarioInteressadoResponseDTO;
import com.project.roomie.dto.update.UsuarioInteressadoUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UsuarioInteressadoPortIn {

    UsuarioInteressado cadastrar(UsuarioInteressado usuarioInteressado);
    ResponseEntity<String> uploadFotoDePerfil(MultipartFile file, Integer id_usuario);
    UsuarioInteressado visualizar(Integer id_usuario);
    UsuarioInteressadoResponseDTO atualizar(Integer id, UsuarioInteressadoUpdateDTO usuarioInteressado);
    FirebaseTokenResponseDTO cadastrarFirebaseToken(String firebase_token, Integer id_usuario);
}