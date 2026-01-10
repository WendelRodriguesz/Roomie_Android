package com.project.roomie.core.service;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.core.model.enums.StatusAnuncio;
import com.project.roomie.dto.create.AnuncioFiltroDTO;
import com.project.roomie.dto.response.AnuncioResponseDTO;
import com.project.roomie.dto.update.AnuncioUpdateDTO;
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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

        anuncio.setStatusAnuncio(StatusAnuncio.ATIVO);

        Anuncio novo_anuncio = anuncioPortOut.save(anuncio);

        UsuarioOfertante usuarioOfertante = usuarioOfertantePortOut.findById(id_usuario);
        usuarioOfertante.setAnuncio(novo_anuncio);
        usuarioOfertantePortOut.save(usuarioOfertante);

        novo_anuncio.setId_usuario_ofertante(id_usuario);

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

            anuncioPortOut.save(anuncio);
            usuario.setAnuncio(anuncio);
            usuarioOfertantePortOut.save(usuario);

            return ResponseEntity.ok(url);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deletarFoto(String urlFoto, Integer id_usuario) {
        try {
            UsuarioOfertante usuario = usuarioOfertantePortOut.findById(id_usuario);

            if (usuario == null || usuario.getAnuncio() == null) {
                return ResponseEntity.badRequest().body("Usuário ou anúncio não encontrado");
            }

            Anuncio anuncio = usuario.getAnuncio();

            if (anuncio.getFotos() == null) {
                anuncio.setFotos(new ArrayList<>());
            }

            String urlDecodificada = URLDecoder.decode(urlFoto, StandardCharsets.UTF_8.toString());
            
            urlDecodificada = urlDecodificada.trim();
            
            String fotoEncontrada = null;
            for (String foto : anuncio.getFotos()) {
                if (foto != null && foto.trim().equals(urlDecodificada)) {
                    fotoEncontrada = foto;
                    break;
                }
            }

            if (fotoEncontrada == null) {
                for (String foto : anuncio.getFotos()) {
                    if (foto != null && foto.trim().equals(urlFoto.trim())) {
                        fotoEncontrada = foto;
                        break;
                    }
                }
            }

            if (fotoEncontrada == null) {
                return ResponseEntity.badRequest().body("Foto não encontrada no anúncio");
            }

            anuncio.getFotos().remove(fotoEncontrada);

            bucketPortOut.delete(fotoEncontrada);

            anuncioPortOut.save(anuncio);
            usuario.setAnuncio(anuncio);
            usuarioOfertantePortOut.save(usuario);

            return ResponseEntity.ok("Foto excluída com sucesso");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao excluir foto: " + e.getMessage());
        }
    }

    @Override
    public AnuncioResponseDTO atualizar(Integer id_anuncio, AnuncioUpdateDTO anuncioUpdateDTO) {
        Anuncio anuncio = anuncioPortOut.findById(id_anuncio);

        if(anuncio == null) {
            throw new RuntimeException("Anuncio não encontrado");
        }

        anuncioMapper.updateAnuncioFromDto(anuncioUpdateDTO, anuncio);

        Anuncio anuncioAtualizado = anuncioPortOut.save(anuncio);

        return anuncioMapper.ModeltoResponseDTO(anuncioAtualizado);
    }

    @Override
    public AnuncioResponseDTO pausarAnuncio(Integer id_anuncio) {
        Anuncio anuncio = anuncioPortOut.findById(id_anuncio);

        if(anuncio.getStatusAnuncio() == StatusAnuncio.PAUSADO) {
            throw new RuntimeException("Anúncio já está pausado");
        }

        anuncio.setStatusAnuncio(StatusAnuncio.PAUSADO);
        anuncioPortOut.save(anuncio);

        return anuncioMapper.ModeltoResponseDTO(anuncio);
    }

    @Override
    public AnuncioResponseDTO reativarAnuncio(Integer id_anuncio) {
        Anuncio anuncio = anuncioPortOut.findById(id_anuncio);

        if(anuncio.getStatusAnuncio() == StatusAnuncio.ATIVO){
            throw new RuntimeException("Anúncio já esta ativo");
        }

        anuncio.setStatusAnuncio(StatusAnuncio.ATIVO);
        anuncioPortOut.save(anuncio);

        return anuncioMapper.ModeltoResponseDTO(anuncio);
    }

    @Override
    public List<AnuncioResponseDTO> visualizarTodos() {
        return anuncioPortOut.buscarAnunciosAtivos()
                .stream()
                .map(anuncioMapper::ModeltoResponseDTO)
                .toList();

    }

    @Override
    public List<AnuncioResponseDTO> filtrar(AnuncioFiltroDTO anuncioFiltroDTO) {
        List<Anuncio> anuncios =
                anuncioPortOut.buscarPorFiltro(anuncioFiltroDTO);

        return anuncios.stream()
                .map(anuncioMapper::ModeltoResponseDTO)
                .toList();
    }

    @Override
    public AnuncioResponseDTO visualizarPorId(Integer id) {
        Anuncio anuncio = anuncioPortOut.findById(id);
        return anuncioMapper.ModeltoResponseDTO(anuncio);
    }
}