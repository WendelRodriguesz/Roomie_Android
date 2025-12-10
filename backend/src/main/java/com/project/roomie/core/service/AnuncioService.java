package com.project.roomie.core.service;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.mapper.AnuncioMapper;
import com.project.roomie.mapper.UsuarioOfertanteMapper;
import com.project.roomie.ports.in.AnuncioPortIn;
import com.project.roomie.ports.out.AnuncioPortOut;
import com.project.roomie.ports.out.BucketPortOut;
import com.project.roomie.ports.out.UsuarioOfertantePortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AnuncioService implements AnuncioPortIn {

    private final AnuncioPortOut anuncioPortOut;
    private final AnuncioMapper anuncioMapper;
    private final UsuarioOfertantePortOut usuarioOfertantePortOut;
    private final UsuarioOfertanteMapper usuarioOfertanteMapper;
    private final BucketPortOut bucketPortOut;

    @Autowired
    public AnuncioService(AnuncioPortOut anuncioPortOut,
                          AnuncioMapper anuncioMapper,
                          UsuarioOfertantePortOut usuarioOfertantePortOut,
                          UsuarioOfertanteMapper usuarioOfertanteMapper,
                          BucketPortOut bucketPortOut) {
        this.anuncioPortOut = anuncioPortOut;
        this.anuncioMapper = anuncioMapper;
        this.usuarioOfertantePortOut = usuarioOfertantePortOut;
        this.usuarioOfertanteMapper = usuarioOfertanteMapper;
        this.bucketPortOut = bucketPortOut;
    }

    @Override
    public Anuncio cadastrar(Anuncio anuncio, Integer id_usuario){
        Anuncio novo_anuncio = anuncioPortOut.save(anuncioMapper.ModeltoJpaEntity(anuncio));

        UsuarioOfertante usuarioOfertante = usuarioOfertantePortOut.findById(id_usuario);
        usuarioOfertante.setAnuncio(novo_anuncio);
        usuarioOfertante = usuarioOfertantePortOut.save(usuarioOfertanteMapper.ModeltoJpaEntity(usuarioOfertante));

        return novo_anuncio;
    }

    @Override
    public ResponseEntity<String> uploadNovaFoto(MultipartFile file, Integer id_usuario){

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo vazio");
        }

        try {
            String url = bucketPortOut.upload(file);

            UsuarioOfertante usuario = usuarioOfertantePortOut.findById(id_usuario);

            if (usuario.getAnuncio().getFotos().size() >= 6){
                return ResponseEntity.badRequest().body("Total máximo de fotos já atingido");
            }

            Anuncio anuncio = usuario.getAnuncio();
            anuncio.getFotos().add(url);

            anuncioPortOut.save(anuncioMapper.ModeltoJpaEntity(anuncio));
            usuario.setAnuncio(anuncio);
            usuarioOfertantePortOut.save(usuarioOfertanteMapper.ModeltoJpaEntity(usuario));

            return ResponseEntity.ok(url);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }
}