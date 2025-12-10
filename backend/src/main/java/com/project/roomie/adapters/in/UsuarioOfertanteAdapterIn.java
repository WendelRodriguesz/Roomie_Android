package com.project.roomie.adapters.in;

import com.project.roomie.dto.create.UsuarioOfertanteCreateDTO;
import com.project.roomie.dto.response.UsuarioOfertanteResponseDTO;
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

    @PostMapping("/uploadFotoDePerfil/{idUsuario}")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @PathVariable Integer idUsuario) {
        return usuarioOfertantePortIn.uploadFotoDePerfil(file, idUsuario);
    }
}