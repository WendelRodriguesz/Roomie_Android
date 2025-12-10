package com.project.roomie.adapters.in;

import com.project.roomie.dto.create.AnuncioCreateDTO;
import com.project.roomie.dto.response.AnuncioResponseDTO;
import com.project.roomie.mapper.AnuncioMapper;
import com.project.roomie.ports.in.AnuncioPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                                        @PathVariable Integer id_usuario ){
        return anuncioMapper.ModeltoResponseDTO(
                anuncioPortIn.cadastrar(
                        anuncioMapper.CreateDTOtoModel(anuncioCreateDTO),
                        id_usuario));
    }
}