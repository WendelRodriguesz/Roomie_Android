package com.project.roomie.infra.persistence.repository.specification;

import com.project.roomie.infra.persistence.entity.AnuncioJpaEntity;
import com.project.roomie.infra.persistence.entity.InteressesOfertantesJpaEntity;
import com.project.roomie.infra.persistence.entity.UsuarioOfertanteJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.TipoImovelJpaEntity;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class AnuncioJpaSpecification {

    public static Specification<AnuncioJpaEntity> cidade(String cidade) {
        return (root, query, cb) ->
                cb.equal(root.get("cidade"), cidade);
    }

    public static Specification<AnuncioJpaEntity> bairro(String bairro) {
        return (root, query, cb) ->
                cb.equal(root.get("bairro"), bairro);
    }

    public static Specification<AnuncioJpaEntity> tipoImovel(TipoImovelJpaEntity tipo) {
        return (root, query, cb) ->
                cb.equal(root.get("tipo_imovel"), tipo);
    }

    public static Specification<AnuncioJpaEntity> vagasMin(Integer vagasMin) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(
                        root.get("vagas_disponiveis"),
                        vagasMin
                );
    }

    public static Specification<AnuncioJpaEntity> custoTotalEntre(
            Float min,
            Float max
    ) {
        return (root, query, cb) -> {

            Expression<Float> custoTotal =
                    cb.sum(
                            root.get("valor_aluguel"),
                            root.get("valor_contas")
                    );

            if (min != null && max != null) {
                return cb.between(custoTotal, min, max);
            }
            if (min != null) {
                return cb.greaterThanOrEqualTo(custoTotal, min);
            }
            if (max != null) {
                return cb.lessThanOrEqualTo(custoTotal, max);
            }
            return cb.conjunction();
        };
    }

    public static Specification<AnuncioJpaEntity> aceitaPets(Boolean aceitaPets) {
        return (root, query, cb) -> {

            Join<AnuncioJpaEntity, UsuarioOfertanteJpaEntity> ofertante =
                    root.join("ofertante");

            Join<UsuarioOfertanteJpaEntity, InteressesOfertantesJpaEntity> interesses =
                    ofertante.join("interesses");

            return cb.equal(
                    interesses.get("aceita_pets"),
                    aceitaPets
            );
        };
    }

    public static Specification<AnuncioJpaEntity> aceitaDividirQuarto(Boolean aceita) {
        return (root, query, cb) -> {

            Join<AnuncioJpaEntity, UsuarioOfertanteJpaEntity> ofertante =
                    root.join("ofertante");

            Join<UsuarioOfertanteJpaEntity, InteressesOfertantesJpaEntity> interesses =
                    ofertante.join("interesses");

            return cb.equal(
                    interesses.get("aceita_dividir_quarto"),
                    aceita
            );
        };
    }
}
