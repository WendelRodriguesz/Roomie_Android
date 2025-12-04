package com.project.roomie.adapters.in;


import com.project.roomie.core.service.InteresseInteressadosService;
import com.project.roomie.mapper.InteressesInteressadosMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.roomie.dto.response.InteressesInteressadosResponseDTO;
import com.project.roomie.dto.create.InteressesInteressadosCreateDTO;

@RestController
@RequestMapping("/api/interesses")
public class InteressesInteressadosAdapterIn {

    private final InteresseInteressadosService interessesService;
    private final InteressesInteressadosMapper interessesInteressadosMapper;

    public InteressesInteressadosAdapterIn(InteresseInteressadosService interessesService, InteressesInteressadosMapper interessesInteressadosMapper) {
        this.interessesService = interessesService;
        this.interessesInteressadosMapper = interessesInteressadosMapper;
    }

    @PostMapping("/cadastrar")
    public InteressesInteressadosResponseDTO cadastrar(@RequestBody InteressesInteressadosCreateDTO interessesInteressadosCreateDTO){
        return interessesInteressadosMapper.modeltoResponseDTO(
                interessesService.cadastrarInteresses(
                        interessesInteressadosMapper.CreateDTOtoModel(interessesInteressadosCreateDTO)));
    }

}
