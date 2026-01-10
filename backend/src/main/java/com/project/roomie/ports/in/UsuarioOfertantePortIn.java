package com.project.roomie.ports.in;

import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.dto.response.FirebaseTokenResponseDTO;
import com.project.roomie.dto.response.UsuarioOfertanteResponseDTO;
import com.project.roomie.dto.update.UsuarioOfertanteUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UsuarioOfertantePortIn {

    UsuarioOfertante cadastrar(UsuarioOfertante usuarioOfertante);
    ResponseEntity<String> uploadFotoDePerfil(MultipartFile file, Integer id_usuario);
    UsuarioOfertante visualizar(Integer id_usuario);
    UsuarioOfertanteResponseDTO atualizar(Integer id, UsuarioOfertanteUpdateDTO usuarioOfertante);
    FirebaseTokenResponseDTO cadastrarFirebaseToken(String firebase_token, Integer id_usuario);
}