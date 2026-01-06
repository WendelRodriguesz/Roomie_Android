package com.project.roomie.adapters.in;

import com.project.roomie.dto.create.InteressesOfertantesCreateDTO;
import com.project.roomie.dto.response.InteressesOfertantesResponseDTO;
import com.project.roomie.dto.update.InteressesOfertantesUpdateDTO;
import com.project.roomie.mapper.InteressesOfertantesMapper;
import com.project.roomie.ports.in.InteressesOfertantesPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interessesOfertantes")
public class InteressesOfertantesAdapterIn {

    private final InteressesOfertantesPortIn interessesOfertantesPortIn;
    private final InteressesOfertantesMapper interessesOfertantesMapper;

    @Autowired
    public InteressesOfertantesAdapterIn(InteressesOfertantesPortIn interessesOfertantesPortIn,
                                         InteressesOfertantesMapper interessesOfertantesMapper){
        this.interessesOfertantesPortIn = interessesOfertantesPortIn;
        this.interessesOfertantesMapper = interessesOfertantesMapper;
    }

    @PostMapping("/cadastrar/{id_usuario}")
    public InteressesOfertantesResponseDTO cadastrar(@RequestBody InteressesOfertantesCreateDTO interessesOfertantesCreateDTO,
                                                     @PathVariable Integer id_usuario ){
        return interessesOfertantesMapper.ModeltoResponseDTO(
                interessesOfertantesPortIn.cadastrarInteresses(
                        interessesOfertantesMapper.CreateDTOtoModel(interessesOfertantesCreateDTO),
                        id_usuario));
    }

    @PatchMapping("/atualizar/{id_interesse}")
    public ResponseEntity<InteressesOfertantesResponseDTO> atualizar(
            @PathVariable Integer id_interesse,
            @RequestBody InteressesOfertantesUpdateDTO interessesOfertantesUpdateDTO
    ){
        return ResponseEntity.ok(interessesOfertantesPortIn.atualizar(id_interesse, interessesOfertantesUpdateDTO ));
    }
}
