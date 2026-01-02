package com.project.roomie.adapters.in;

import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.dto.response.UsuarioOfertanteResponseDTO;
import com.project.roomie.mapper.UsuarioOfertanteMapper;
import com.project.roomie.ports.in.MatchPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/match")
public class MatchAdapterIn {

    private final MatchPortIn matchPortIn;
    private final UsuarioOfertanteMapper usuarioOfertanteMapper;

    @Autowired
    public MatchAdapterIn(MatchPortIn matchPortIn,
                          UsuarioOfertanteMapper usuarioOfertanteMapper){
        this.matchPortIn = matchPortIn;
        this.usuarioOfertanteMapper = usuarioOfertanteMapper;
    }

    @GetMapping
    public Page<UsuarioOfertanteResponseDTO> buscarMatches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam Integer id_usuario_interessado
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UsuarioOfertante> pagina =  matchPortIn.buscarMatches(id_usuario_interessado, pageable);

        List<UsuarioOfertanteResponseDTO> usuarios = usuarioOfertanteMapper.ModeltoResponseDTO(pagina.getContent());

        return new PageImpl<>(usuarios, pageable, pagina.getTotalElements());
    }
}