package com.project.roomie.core.service;

import com.project.roomie.core.model.UsuarioInteressado;
import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.dto.response.FirebaseTokenResponseDTO;
import com.project.roomie.dto.response.UsuarioOfertanteResponseDTO;
import com.project.roomie.dto.update.UsuarioOfertanteUpdateDTO;
import com.project.roomie.mapper.UsuarioOfertanteMapper;
import com.project.roomie.ports.in.UsuarioOfertantePortIn;
import com.project.roomie.ports.out.BucketPortOut;
import com.project.roomie.ports.out.UsuarioOfertantePortOut;
import com.project.roomie.util.AgeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioOfertanteService implements UsuarioOfertantePortIn {

    private final UsuarioOfertantePortOut usuarioOfertantePortOut;
    private final UsuarioOfertanteMapper usuarioOfertanteMapper;
    private final PasswordEncoder passwordEncoder;
    private final BucketPortOut bucketPortOut;
    private final AgeCalculator ageCalculator;

    @Autowired
    public UsuarioOfertanteService(UsuarioOfertantePortOut usuarioOfertantePortOut,
                                   UsuarioOfertanteMapper usuarioOfertanteMapper,
                                   PasswordEncoder passwordEncoder,
                                   BucketPortOut bucketPortOut,
                                   AgeCalculator ageCalculator){
        this.usuarioOfertantePortOut = usuarioOfertantePortOut;
        this.usuarioOfertanteMapper = usuarioOfertanteMapper;
        this.passwordEncoder = passwordEncoder;
        this.bucketPortOut = bucketPortOut;
        this.ageCalculator = ageCalculator;
    }

    @Override
    public UsuarioOfertante cadastrar(UsuarioOfertante usuarioOfertante){

        usuarioOfertante.setSenha(passwordEncoder.encode(usuarioOfertante.getSenha()));
        usuarioOfertante.setIdade(ageCalculator.calcularIdade(usuarioOfertante.getData_de_nascimento()));
        return usuarioOfertantePortOut.save(usuarioOfertante);
    }

    @Override
    public ResponseEntity<String> uploadFotoDePerfil(MultipartFile file, Integer id_usuario){

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo vazio");
        }

        try {
            String url = bucketPortOut.upload(file);
            UsuarioOfertante usuario = usuarioOfertantePortOut.findById(id_usuario);
            usuario.setFoto_de_perfil(url);
            usuarioOfertantePortOut.save(usuario);
            return ResponseEntity.ok(url);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }

    @Override
    public UsuarioOfertante visualizar(Integer id_usuario){
        return usuarioOfertantePortOut.findById(id_usuario);
    }

    @Override
    public UsuarioOfertanteResponseDTO atualizar(Integer id, UsuarioOfertanteUpdateDTO usuarioOfertante) {
        UsuarioOfertante usuario = usuarioOfertantePortOut.findById(id);

        if(usuario == null){
            throw new RuntimeException("Usuário não encontrado");
        }

        usuarioOfertanteMapper.updateUsuarioFromDto(usuarioOfertante, usuario);

        UsuarioOfertante usuarioAtualizado = usuarioOfertantePortOut.save(usuario);

        return usuarioOfertanteMapper.ModeltoResponseDTO(usuarioAtualizado);

    }

    @Override
    public FirebaseTokenResponseDTO cadastrarFirebaseToken(String firebase_token, Integer id_usuario){

        UsuarioOfertante usuarioOfertante = usuarioOfertantePortOut.findById(id_usuario);
        usuarioOfertante.setFirebase_token(firebase_token);
        usuarioOfertantePortOut.save(usuarioOfertante);
        return new FirebaseTokenResponseDTO(usuarioOfertante.getFirebase_token());
    }
}