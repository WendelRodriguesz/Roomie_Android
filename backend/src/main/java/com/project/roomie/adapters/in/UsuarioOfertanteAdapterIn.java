package com.project.roomie.adapters.in;

import com.project.roomie.dto.create.UsuarioOfertanteCreateDTO;
import com.project.roomie.dto.response.UsuarioOfertanteResponseDTO;
import com.project.roomie.dto.update.UsuarioOfertanteUpdateDTO;
import com.project.roomie.mapper.UsuarioOfertanteMapper;
import com.project.roomie.ports.in.UsuarioOfertantePortIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/usuarioOfertante")
public class UsuarioOfertanteAdapterIn {

    private final UsuarioOfertantePortIn usuarioOfertantePortIn;
    private final UsuarioOfertanteMapper usuarioOfertanteMapper;

    public UsuarioOfertanteAdapterIn(UsuarioOfertantePortIn usuarioOfertantePortIn,
                                       UsuarioOfertanteMapper usuarioOfertanteMapper){
        this.usuarioOfertantePortIn = usuarioOfertantePortIn;
        this.usuarioOfertanteMapper = usuarioOfertanteMapper;
    }

    @PostMapping("/cadastrar")
    public UsuarioOfertanteResponseDTO cadastrar(@RequestBody UsuarioOfertanteCreateDTO usuarioOfertanteCreateDTO) {
        return usuarioOfertanteMapper.ModeltoResponseDTO(
                usuarioOfertantePortIn.cadastrar(
                        usuarioOfertanteMapper.CreateDTOtoModel(
                                usuarioOfertanteCreateDTO)));
    }

    @PostMapping("/uploadFotoDePerfil/{id_usuario}")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @PathVariable Integer id_usuario) {
        return usuarioOfertantePortIn.uploadFotoDePerfil(file, id_usuario);
    }

    @GetMapping("/visualizar/{id_usuario}")
    public UsuarioOfertanteResponseDTO visualizar(@PathVariable Integer id_usuario){
        return usuarioOfertanteMapper.ModeltoResponseDTO(
                usuarioOfertantePortIn.visualizar(id_usuario)
        );
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioOfertanteResponseDTO> atualizar(
            @PathVariable Integer id,
            @RequestBody UsuarioOfertanteUpdateDTO usuarioOfertanteUpdateDTO ) {
        return ResponseEntity.ok(usuarioOfertantePortIn.atualizar(id, usuarioOfertanteUpdateDTO));
    }
}