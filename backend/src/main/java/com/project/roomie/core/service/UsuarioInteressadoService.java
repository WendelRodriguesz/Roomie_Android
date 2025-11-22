package com.project.roomie.core.service;

import com.project.roomie.core.model.Usuario;
import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.mapper.UsuarioInteressadoMapper;
import com.project.roomie.ports.in.UsuarioInteressadoPortIn;
import com.project.roomie.ports.out.BucketPortOut;
import com.project.roomie.ports.out.UsuarioInteressadoPortOut;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioInteressadoService implements UsuarioInteressadoPortIn {

    private final UsuarioInteressadoPortOut usuarioInteressadoPortOut;
    private final UsuarioInteressadoMapper usuarioInteressadoMapper;
    private final PasswordEncoder passwordEncoder;
    private final BucketPortOut bucketPortOut;

    @Autowired
    public UsuarioInteressadoService(UsuarioInteressadoPortOut usuarioInteressadoPortOut,
                                     UsuarioInteressadoMapper usuarioInteressadoMapper,
                                     PasswordEncoder passwordEncoder,
                                     BucketPortOut bucketPortOut){
        this.usuarioInteressadoPortOut = usuarioInteressadoPortOut;
        this.usuarioInteressadoMapper = usuarioInteressadoMapper;
        this.passwordEncoder = passwordEncoder;
        this.bucketPortOut = bucketPortOut;
    }

    @Override
    public UsuarioInteressado cadastrar(UsuarioInteressado usuarioInteressado){

        usuarioInteressado.setSenha(passwordEncoder.encode(usuarioInteressado.getSenha()));
        return usuarioInteressadoPortOut.save(usuarioInteressadoMapper.ModeltoJpaEntity(usuarioInteressado));
    }

    @Override
    public ResponseEntity<String> uploadFotoDePerfil(MultipartFile file, Integer idUsuario){

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo vazio");
        }

        try {
            String url = bucketPortOut.upload(file);
            Usuario usuario = usuarioInteressadoPortOut.findById(idUsuario);
            usuario.setFoto_de_perfil(url);
            return ResponseEntity.ok(url);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }
}