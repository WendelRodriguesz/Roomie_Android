package com.project.roomie.adapters.in;

import com.project.roomie.core.model.UsuarioOfertante;
import com.project.roomie.dto.response.MatchResponseDTO;
import com.project.roomie.dto.response.UsuarioOfertanteResponseDTO;
import com.project.roomie.mapper.MatchMapper;
import com.project.roomie.mapper.UsuarioOfertanteMapper;
import com.project.roomie.ports.in.MatchPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match")
public class MatchAdapterIn {

    private final MatchPortIn matchPortIn;
    private final UsuarioOfertanteMapper usuarioOfertanteMapper;
    private final MatchMapper matchMapper;

    @Autowired
    public MatchAdapterIn(MatchPortIn matchPortIn,
                          UsuarioOfertanteMapper usuarioOfertanteMapper,
                          MatchMapper matchMapper){
        this.matchPortIn = matchPortIn;
        this.usuarioOfertanteMapper = usuarioOfertanteMapper;
        this.matchMapper = matchMapper;
    }

    @GetMapping("/buscarCandidatos")
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

    @PostMapping("/enviarLike")
    public MatchResponseDTO buscarMatches(
            @RequestParam Integer id_usuario_interessado,
            @RequestParam Integer id_usuario_ofertante
    ) {
        return matchMapper.ModeltoResponseDTO(matchPortIn.enviarLike(id_usuario_interessado, id_usuario_ofertante));
    }

    @PostMapping("/aceitar/{id_match}")
    public MatchResponseDTO aceitarMatch(@PathVariable Integer id_match) {
        return matchMapper.ModeltoResponseDTO(matchPortIn.aceitarMatch(id_match));
    }
}