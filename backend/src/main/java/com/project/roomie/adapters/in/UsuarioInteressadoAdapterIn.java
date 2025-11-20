package com.project.roomie.adapters.in;

import com.project.roomie.core.service.UsuarioInteressadoService;
import com.project.roomie.dto.create.UsuarioInteressadoCreateDTO;
import com.project.roomie.dto.response.UsuarioInteressadoResponseDTO;
import com.project.roomie.mapper.UsuarioInteressadoMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarioInteressado")
public class UsuarioInteressadoAdapterIn {

    private final UsuarioInteressadoService usuarioInteressadoService;
    private final UsuarioInteressadoMapper usuarioInteressadoMapper;

    public UsuarioInteressadoAdapterIn(UsuarioInteressadoService usuarioInteressadoService,
                                       UsuarioInteressadoMapper usuarioInteressadoMapper){
        this.usuarioInteressadoService = usuarioInteressadoService;
        this.usuarioInteressadoMapper = usuarioInteressadoMapper;
    }

    @PostMapping("/cadastrar")
    public UsuarioInteressadoResponseDTO cadastrar(@RequestBody UsuarioInteressadoCreateDTO usuarioInteressadoCreateDTO) {
        return usuarioInteressadoMapper.ModeltoResponseDTO(
                usuarioInteressadoService.cadastrar(
                        usuarioInteressadoMapper.CreateDTOtoModel(
                                usuarioInteressadoCreateDTO)));
    }
}