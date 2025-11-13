package com.project.roomie.adapters.in;

import com.project.roomie.core.service.UsuarioOfertanteService;
import com.project.roomie.dto.create.UsuarioOfertanteCreateDTO;
import com.project.roomie.dto.response.UsuarioOfertanteResponseDTO;
import com.project.roomie.mapper.UsuarioOfertanteMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarioOfertante")
public class UsuarioOfertanteAdapterIn {

    private final UsuarioOfertanteService usuarioOfertanteService;
    private final UsuarioOfertanteMapper usuarioOfertanteMapper;

    public UsuarioOfertanteAdapterIn(UsuarioOfertanteService usuarioOfertanteService,
                                       UsuarioOfertanteMapper usuarioOfertanteMapper){
        this.usuarioOfertanteService = usuarioOfertanteService;
        this.usuarioOfertanteMapper = usuarioOfertanteMapper;
    }

    @PostMapping("/cadastrar")
    public UsuarioOfertanteResponseDTO cadastrar(@RequestBody UsuarioOfertanteCreateDTO usuarioOfertanteCreateDTO) {
        return usuarioOfertanteMapper.ModeltoResponseDTO(
                usuarioOfertanteService.cadastrar(
                        usuarioOfertanteMapper.CreateDTOtoModel(
                                usuarioOfertanteCreateDTO)));
    }
}