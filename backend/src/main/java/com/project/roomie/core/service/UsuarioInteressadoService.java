package com.project.roomie.core.service;

import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.dto.response.UsuarioInteressadoResponseDTO;
import com.project.roomie.dto.update.UsuarioInteressadoUpdateDTO;
import com.project.roomie.mapper.UsuarioInteressadoMapper;
import com.project.roomie.ports.in.UsuarioInteressadoPortIn;
import com.project.roomie.ports.out.BucketPortOut;
import com.project.roomie.ports.out.UsuarioInteressadoPortOut;
import com.project.roomie.util.AgeCalculator;
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
    private final AgeCalculator ageCalculator;

    @Autowired
    public UsuarioInteressadoService(UsuarioInteressadoPortOut usuarioInteressadoPortOut,
                                     UsuarioInteressadoMapper usuarioInteressadoMapper,
                                     PasswordEncoder passwordEncoder,
                                     BucketPortOut bucketPortOut,
                                     AgeCalculator ageCalculator){
        this.usuarioInteressadoPortOut = usuarioInteressadoPortOut;
        this.usuarioInteressadoMapper = usuarioInteressadoMapper;
        this.passwordEncoder = passwordEncoder;
        this.bucketPortOut = bucketPortOut;
        this.ageCalculator = ageCalculator;
    }

    @Override
    public UsuarioInteressado cadastrar(UsuarioInteressado usuarioInteressado){

        usuarioInteressado.setSenha(passwordEncoder.encode(usuarioInteressado.getSenha()));
        usuarioInteressado.setIdade(ageCalculator.calcularIdade(usuarioInteressado.getData_de_nascimento()));
        return usuarioInteressadoPortOut.save(usuarioInteressado);
    }

    @Override
    public ResponseEntity<String> uploadFotoDePerfil(MultipartFile file, Integer id_usuario){

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo vazio");
        }

        try {
            String url = bucketPortOut.upload(file);

            UsuarioInteressado usuario = usuarioInteressadoPortOut.findById(id_usuario);
            usuario.setFoto_de_perfil(url);
            usuarioInteressadoPortOut.save(usuario);

            return ResponseEntity.ok(url);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }

    @Override
    public UsuarioInteressado visualizar(Integer id_usuario){
        return usuarioInteressadoPortOut.findById(id_usuario);
    }

    @Override
    public UsuarioInteressadoResponseDTO atualizar(Integer id, UsuarioInteressadoUpdateDTO usuarioInteressado) {

        UsuarioInteressado usuario = usuarioInteressadoPortOut.findById(id);

        if(usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        usuarioInteressadoMapper.updateUsuarioFromDto(usuarioInteressado, usuario);

        UsuarioInteressado usuarioAtualizado = usuarioInteressadoPortOut.save(usuario);

        return usuarioInteressadoMapper.ModeltoResponseDTO(usuarioAtualizado);
    }



}