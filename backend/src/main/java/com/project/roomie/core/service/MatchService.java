package com.project.roomie.core.service;

import com.project.roomie.core.model.*;
import com.project.roomie.ports.in.MatchPortIn;
import com.project.roomie.ports.out.UsuarioInteressadoPortOut;
import com.project.roomie.ports.out.UsuarioOfertantePortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MatchService implements MatchPortIn {

    private final UsuarioOfertantePortOut usuarioOfertantePortOut;
    private final UsuarioInteressadoPortOut usuarioInteressadoPortOut;

    @Autowired
    public MatchService(UsuarioOfertantePortOut usuarioOfertantePortOut,
                        UsuarioInteressadoPortOut usuarioInteressadoPortOut){
        this.usuarioOfertantePortOut = usuarioOfertantePortOut;
        this.usuarioInteressadoPortOut = usuarioInteressadoPortOut;
    }

    @Override
    public Page<UsuarioOfertante> buscarMatches(Integer id_usuario_interessado, Pageable pageable){

        // Busca dados base
        UsuarioInteressado interessado = usuarioInteressadoPortOut.findById(id_usuario_interessado);
        List<UsuarioOfertante> ofertantes = usuarioOfertantePortOut.buscarCandidatosMatch();

        log.error("OFERTANTES ENCONTRADOS: {}", ofertantes.size());

        // Aplica regra de match + score
        List<UsuarioOfertante> matchesOrdenados = ofertantes.stream()
                .map(ofertante -> Map.entry(
                        ofertante,
                        calcularScore(
                                interessado.getInteresses(),
                                ofertante.getInteresses(),
                                ofertante.getAnuncio()
                        )
                ))
                .filter(entry -> entry.getValue() > 0)
                .sorted(Map.Entry.<UsuarioOfertante, Integer>
                        comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), matchesOrdenados.size());

        List<UsuarioOfertante> pagina =
                start >= matchesOrdenados.size()
                        ? List.of()
                        : matchesOrdenados.subList(start, end);

        return new PageImpl<>(pagina, pageable, matchesOrdenados.size());
    }

    public int calcularScore(
            InteressesInteressados interessado,
            InteressesOfertantes ofertante,
            Anuncio anuncio
    ) {

//        // Regra eliminatória: orçamento
//        if (anuncio.getValor_aluguel() + anuncio.getValor_contas() > interessado.getOrcamento_max()) {
//            return 0;
//        }
//
//        // Regra eliminatória: dividir quarto
//        if (!Objects.equals(
//                interessado.isAceita_dividir_quarto(),
//                ofertante.isAceita_dividir_quarto()
//        )) {
//            return 0;
//        }
//
//        int score = 30; // regras eliminatórias já eliminadas
//
//        if (Objects.equals(
//                interessado.isAceita_pets(),
//                ofertante.isAceita_pets()
//        )) {
//            score += 15;
//        }
//
//        if (interessado.getHorario_sono()
//                .equals(ofertante.getHorario_sono())) {
//            score += 15;
//        }
//
//        if (interessado.getHabitos_limpeza()
//                .equals(ofertante.getHabitos_limpeza())) {
//            score += 15;
//        }
//
//        if (interessado.getFrequencia_festas()
//                .equals(ofertante.getFrequencia_festas())) {
//            score += 10;
//        }

        return 10;
    }
}