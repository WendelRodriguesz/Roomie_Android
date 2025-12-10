package com.project.roomie.adapters.in;

import com.project.roomie.dto.create.AnuncioCreateDTO;
import com.project.roomie.dto.response.AnuncioResponseDTO;
import com.project.roomie.mapper.AnuncioMapper;
import com.project.roomie.ports.in.AnuncioPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/anuncio")
public class AnuncioAdapterIn {

    private final AnuncioPortIn anuncioPortIn;
    private final AnuncioMapper anuncioMapper;

    @Autowired
    public AnuncioAdapterIn(AnuncioPortIn anuncioPortIn,
                            AnuncioMapper anuncioMapper) {
        this.anuncioPortIn = anuncioPortIn;
        this.anuncioMapper = anuncioMapper;
    }

    @PostMapping("/cadastrar/{id_usuario}")
    public AnuncioResponseDTO cadastrar(@RequestBody AnuncioCreateDTO anuncioCreateDTO,
                                        @PathVariable Integer id_usuario){
        return anuncioMapper.ModeltoResponseDTO(
                anuncioPortIn.cadastrar(
                        anuncioMapper.CreateDTOtoModel(anuncioCreateDTO),
                        id_usuario));
    }

    @PostMapping("/uploadNovaFoto/{id_usuario}")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @PathVariable Integer id_usuario) {
        return anuncioPortIn.uploadNovaFoto(file, id_usuario);
    }
}