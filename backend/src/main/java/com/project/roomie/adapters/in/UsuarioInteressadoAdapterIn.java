package com.project.roomie.adapters.in;

import com.project.roomie.core.service.UsuarioInteressadoService;
import com.project.roomie.dto.create.UsuarioInteressadoCreateDTO;
import com.project.roomie.dto.response.UsuarioInteressadoResponseDTO;
import com.project.roomie.mapper.UsuarioInteressadoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/uploadFotoDePerfil/{id_usuario}")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @PathVariable Integer id_usuario) {
        return usuarioInteressadoService.uploadFotoDePerfil(file, id_usuario);
    }

    @GetMapping("/uploadFotoDePerfil/{id_usuario}")
    public UsuarioInteressadoResponseDTO visualizar(@PathVariable Integer id_usuario){
        return usuarioInteressadoMapper.ModeltoResponseDTO(
                usuarioInteressadoService.visualizar(id_usuario)
        );
    }
}