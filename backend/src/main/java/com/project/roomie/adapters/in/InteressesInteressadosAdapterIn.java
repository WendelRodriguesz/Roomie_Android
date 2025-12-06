package com.project.roomie.adapters.in;

import com.project.roomie.core.service.InteresseInteressadosService;
import com.project.roomie.mapper.InteressesInteressadosMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.project.roomie.dto.response.InteressesInteressadosResponseDTO;
import com.project.roomie.dto.create.InteressesInteressadosCreateDTO;

@RestController
@RequestMapping("/api/interessesInteressados")
public class InteressesInteressadosAdapterIn {

    private final InteresseInteressadosService interessesService;
    private final InteressesInteressadosMapper interessesInteressadosMapper;

    @Autowired
    public InteressesInteressadosAdapterIn(InteresseInteressadosService interessesService,
                                           InteressesInteressadosMapper interessesInteressadosMapper) {
        this.interessesService = interessesService;
        this.interessesInteressadosMapper = interessesInteressadosMapper;
    }

    @PostMapping("/cadastrar/{id_usuario}")
    public InteressesInteressadosResponseDTO cadastrar(@RequestBody InteressesInteressadosCreateDTO interessesInteressadosCreateDTO,
                                                       @PathVariable Integer id_usuario ){
        return interessesInteressadosMapper.ModeltoResponseDTO(
                interessesService.cadastrarInteresses(
                        interessesInteressadosMapper.CreateDTOtoModel(interessesInteressadosCreateDTO),
                        id_usuario));
    }
}