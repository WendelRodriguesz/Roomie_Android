package com.project.roomie.adapters.in;

import com.project.roomie.dto.create.InteressesInteressadosCreateDTO;
import com.project.roomie.dto.response.InteressesInteressadosResponseDTO;
import com.project.roomie.mapper.InteressesInteressadosMapper;
import com.project.roomie.ports.in.InteressesInteressadosPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interessesInteressados")
public class InteressesInteressadosAdapterIn {

    private final InteressesInteressadosPortIn interessesInteressadosPortIn;
    private final InteressesInteressadosMapper interessesInteressadosMapper;

    @Autowired
    public InteressesInteressadosAdapterIn(InteressesInteressadosPortIn interessesInteressadosPortIn,
                                           InteressesInteressadosMapper interessesInteressadosMapper) {
        this.interessesInteressadosPortIn = interessesInteressadosPortIn;
        this.interessesInteressadosMapper = interessesInteressadosMapper;
    }

    @PostMapping("/cadastrar/{id_usuario}")
    public InteressesInteressadosResponseDTO cadastrar(@RequestBody InteressesInteressadosCreateDTO interessesInteressadosCreateDTO,
                                                       @PathVariable Integer id_usuario ){
        return interessesInteressadosMapper.ModeltoResponseDTO(
                interessesInteressadosPortIn.cadastrar(
                        interessesInteressadosMapper.CreateDTOtoModel(interessesInteressadosCreateDTO),
                        id_usuario));
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<InteressesInteressadosResponseDTO> atualizar(
            @PathVariable Integer id,
            @RequestBody InteressesInteressadosUpdateDTO interessesInteressadosUpdateDTO
    ){
        return ResponseEntity.ok(interessesInteressadosPortIn.atualizar(id, interessesInteressadosUpdateDTO));
    }
}