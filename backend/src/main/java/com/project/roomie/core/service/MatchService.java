package com.project.roomie.core.service;

import com.project.roomie.core.model.*;
import com.project.roomie.core.model.enums.MatchStatus;
import com.project.roomie.infra.persistence.entity.MatchJpaEntity;
import com.project.roomie.mapper.MatchMapper;
import com.project.roomie.ports.in.MatchPortIn;
import com.project.roomie.ports.out.MatchPortOut;
import com.project.roomie.ports.out.NotificacoesPortOut;
import com.project.roomie.ports.out.UsuarioInteressadoPortOut;
import com.project.roomie.ports.out.UsuarioOfertantePortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MatchService implements MatchPortIn {

    private final UsuarioOfertantePortOut usuarioOfertantePortOut;
    private final UsuarioInteressadoPortOut usuarioInteressadoPortOut;
    private final MatchPortOut matchPortOut;
    private final MatchMapper matchMapper;
    private final NotificacoesPortOut notificacoesPortOut;

    @Autowired
    public MatchService(UsuarioOfertantePortOut usuarioOfertantePortOut,
                        UsuarioInteressadoPortOut usuarioInteressadoPortOut,
                        MatchPortOut matchPortOut,
                        MatchMapper matchMapper,
                        NotificacoesPortOut notificacoesPortOut){
        this.usuarioOfertantePortOut = usuarioOfertantePortOut;
        this.usuarioInteressadoPortOut = usuarioInteressadoPortOut;
        this.matchPortOut = matchPortOut;
        this.matchMapper = matchMapper;
        this.notificacoesPortOut = notificacoesPortOut;
    }

    @Override
    public Page<UsuarioOfertante> buscarMatches(Integer id_usuario_interessado, Pageable pageable){

        UsuarioInteressado interessado = usuarioInteressadoPortOut.findById(id_usuario_interessado);

        if(interessado.getInteresses() == null){
            throw new RuntimeException("Usuário interessado sem interesses cadastrados");
        }

        List<UsuarioOfertante> ofertantes = usuarioOfertantePortOut.buscarCandidatosMatch(id_usuario_interessado);

        List<UsuarioOfertante> matchesOrdenados = ofertantes.stream()
                .map(ofertante -> Map.entry(
                        ofertante,
                        calcularScore(
                                interessado.getInteresses(),
                                ofertante.getInteresses(),
                                ofertante.getAnuncio()
                        )
                ))
                .filter(entry -> entry.getValue() >= 30)
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

    @Override
    public Match enviarLike(Integer id_usuario_interessado, Integer id_usuario_ofertante){

        UsuarioInteressado interessado = usuarioInteressadoPortOut.findById(id_usuario_interessado);
        UsuarioOfertante ofertante = usuarioOfertantePortOut.findById(id_usuario_ofertante);

        Match match = new Match(interessado, ofertante, MatchStatus.PENDENTE);
        return matchPortOut.save(matchMapper.ModeltoJpaEntity(match));
    }

    @Override
    public Match aceitarMatch(Integer id_match){

        Match match = matchPortOut.findById(id_match);

        if (match.getStatus().equals(MatchStatus.ACEITO)){
            throw new RuntimeException("Match já foi aceito antes");
        }

        if (match.getStatus().equals(MatchStatus.RECUSADO)){
            throw new RuntimeException("Match já foi recusado");
        }

        notificacoesPortOut.enviar(
                match.getInteressado().getFirebase_token(),
                "Fulano aceitou seu match"
                );

        match.setStatus(MatchStatus.ACEITO);
        return matchPortOut.save(matchMapper.ModeltoJpaEntity(match));
    }

    @Override
    public Match recusarMatch(Integer id_match){

        Match match = matchPortOut.findById(id_match);

        if (match.getStatus().equals(MatchStatus.ACEITO)){
            throw new RuntimeException("Match já foi aceito");
        }

        if (match.getStatus().equals(MatchStatus.RECUSADO)){
            throw new RuntimeException("Match já foi recusado antes");
        }

        match.setStatus(MatchStatus.RECUSADO);
        return matchPortOut.save(matchMapper.ModeltoJpaEntity(match));
    }

    public Page<Match> visualizarMeusLikes(Integer id_ofertante, Pageable pageable) {

        return matchPortOut.findByOfertante(id_ofertante, pageable);
    }

    public int calcularScore(
            InteressesInteressados interessado,
            InteressesOfertantes ofertante,
            Anuncio anuncio
    ) {

        if (anuncio.getValor_aluguel() + anuncio.getValor_contas() > interessado.getOrcamento_max()) {
            return 0;
        }

        if (!Objects.equals(
                interessado.isAceita_dividir_quarto(),
                ofertante.isAceita_dividir_quarto()
        )) {
            return 0;
        }

        int score = 30;

        if (Objects.equals(
                interessado.isAceita_pets(),
                ofertante.isAceita_pets()
        )) {
            score += 15;
        }

        if (interessado.getHorario_sono()
                .equals(ofertante.getHorario_sono())) {
            score += 15;
        }

        if (interessado.getHabitos_limpeza()
                .equals(ofertante.getHabitos_limpeza())) {
            score += 15;
        }

        if (interessado.getFrequencia_festas()
                .equals(ofertante.getFrequencia_festas())) {
            score += 10;
        }

        return score;
    }
}