package com.project.roomie.adapters.in;


import com.project.roomie.core.service.InteresseService;
import com.project.roomie.mapper.InteressesMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.roomie.dto.response.InteressesResponseDTO;
import com.project.roomie.dto.create.InteressesCreateDTO;

@RestController
@RequestMapping("/api/interesses")
public class InteressesAdapterIn {

    private final InteresseService interessesService;
    private final InteressesMapper interessesMapper;

    public InteressesAdapterIn(InteresseService interessesService, InteressesMapper interessesMapper) {
        this.interessesService = interessesService;
        this.interessesMapper = interessesMapper;
    }

    @PostMapping("/cadastrar")
    public InteressesResponseDTO cadastrar(@RequestBody InteressesCreateDTO interessesCreateDTO){
        return interessesMapper.modeltoResponseDTO(
                interessesService.cadastrarInteresses(
                        interessesMapper.CreateDTOtoModel(interessesCreateDTO)));
    }

}
