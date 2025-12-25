package com.project.roomie.adapters.in;

import com.project.roomie.dto.create.UsuarioInteressadoCreateDTO;
import com.project.roomie.dto.response.UsuarioInteressadoResponseDTO;
import com.project.roomie.dto.update.UsuarioInteressadoUpdateDTO;
import com.project.roomie.mapper.UsuarioInteressadoMapper;
import com.project.roomie.ports.in.UsuarioInteressadoPortIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/usuarioInteressado")
public class UsuarioInteressadoAdapterIn {

    private final UsuarioInteressadoPortIn usuarioInteressadoPortIn;
    private final UsuarioInteressadoMapper usuarioInteressadoMapper;

    public UsuarioInteressadoAdapterIn(UsuarioInteressadoPortIn usuarioInteressadoPortIn,
                                       UsuarioInteressadoMapper usuarioInteressadoMapper){
        this.usuarioInteressadoPortIn = usuarioInteressadoPortIn;
        this.usuarioInteressadoMapper = usuarioInteressadoMapper;
    }

    @PostMapping("/cadastrar")
    public UsuarioInteressadoResponseDTO cadastrar(@RequestBody UsuarioInteressadoCreateDTO usuarioInteressadoCreateDTO) {
        return usuarioInteressadoMapper.ModeltoResponseDTO(
                usuarioInteressadoPortIn.cadastrar(
                        usuarioInteressadoMapper.CreateDTOtoModel(
                                usuarioInteressadoCreateDTO)));
    }

    @PostMapping("/uploadFotoDePerfil/{id_usuario}")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @PathVariable Integer id_usuario) {
        return usuarioInteressadoPortIn.uploadFotoDePerfil(file, id_usuario);
    }

    @GetMapping("/visualizar/{id_usuario}")
    public UsuarioInteressadoResponseDTO visualizar(@PathVariable Integer id_usuario){
        return usuarioInteressadoMapper.ModeltoResponseDTO(
                usuarioInteressadoPortIn.visualizar(id_usuario)
        );
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioInteressadoResponseDTO> atualizar(
            @PathVariable Integer id,
            @RequestBody UsuarioInteressadoUpdateDTO dto
    ) {
        return ResponseEntity.ok(usuarioInteressadoPortIn.atualizar(id, dto));
    }

}