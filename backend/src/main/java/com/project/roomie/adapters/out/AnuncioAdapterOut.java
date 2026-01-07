package com.project.roomie.adapters.out;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.core.model.enums.StatusAnuncio;
import com.project.roomie.dto.create.AnuncioFiltroDTO;
import com.project.roomie.infra.persistence.entity.AnuncioJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.StatusAnuncioJpaEntity;
import com.project.roomie.infra.persistence.entity.enums.TipoImovelJpaEntity;
import com.project.roomie.infra.persistence.repository.AnuncioRepository;
import com.project.roomie.infra.persistence.repository.specification.AnuncioJpaSpecification;
import com.project.roomie.mapper.AnuncioMapper;
import com.project.roomie.ports.out.AnuncioPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnuncioAdapterOut implements AnuncioPortOut {

    private final AnuncioRepository anuncioRepository;
    private final AnuncioMapper anuncioMapper;

    @Autowired
    public AnuncioAdapterOut(AnuncioRepository anuncioRepository,
                                      AnuncioMapper anuncioMapper){
        this.anuncioRepository = anuncioRepository;
        this.anuncioMapper = anuncioMapper;
    }

    @Override
    public Anuncio save(Anuncio anuncio){
        AnuncioJpaEntity entity =
                anuncioMapper.ModeltoJpaEntity(anuncio);

        AnuncioJpaEntity salvo =
                anuncioRepository.save(entity);

        return anuncioMapper.JpaEntitytoModel(salvo);
    }

    @Override
    public Anuncio findById(Integer id){
        AnuncioJpaEntity AnuncioJpaEntity = anuncioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anúncio não encontrado"));

        return anuncioMapper.JpaEntitytoModel(AnuncioJpaEntity);
    }

    @Override
    public List<Anuncio> buscarAnunciosAtivos() {
        StatusAnuncioJpaEntity statusJpa =
                StatusAnuncioJpaEntity.ATIVO;

        return anuncioRepository.findByStatusAnuncio(statusJpa)
                .stream()
                .map(anuncioMapper::JpaEntitytoModel)
                .toList();
    }

    @Override
    public List<Anuncio> buscarPorFiltro(AnuncioFiltroDTO anuncioFiltroDTO) {

        Specification<AnuncioJpaEntity> spec = Specification.where(null);

        if(anuncioFiltroDTO.getCidade() != null){
            spec = spec.and(
                    AnuncioJpaSpecification.cidade(anuncioFiltroDTO.getCidade())
            );
        }

        if(anuncioFiltroDTO.getBairro() != null){
            spec = spec.and(
                    AnuncioJpaSpecification.bairro(anuncioFiltroDTO.getBairro())
            );
        }

        if (anuncioFiltroDTO.getTipoImovel() != null) {
            TipoImovelJpaEntity tipoJpa =
                    TipoImovelJpaEntity.valueOf(
                            anuncioFiltroDTO.getTipoImovel().name()
                    );

            spec = spec.and(
                    AnuncioJpaSpecification.tipoImovel(tipoJpa)
            );
        }

        if (anuncioFiltroDTO.getVagasMin() != null) {
            spec = spec.and(
                    AnuncioJpaSpecification.vagasMin(anuncioFiltroDTO.getVagasMin())
            );
        }

        if (anuncioFiltroDTO.getCustoMin() != null || anuncioFiltroDTO.getCustoMax() != null) {
            spec = spec.and(
                    AnuncioJpaSpecification.custoTotalEntre(
                            anuncioFiltroDTO.getCustoMin(),
                            anuncioFiltroDTO.getCustoMax()
                    )
            );
        }

        if (anuncioFiltroDTO.getAceitaPets() != null) {
            spec = spec.and(
                    AnuncioJpaSpecification.aceitaPets(anuncioFiltroDTO.getAceitaPets())
            );
        }

        if (anuncioFiltroDTO.getAceitaDividirQuarto() != null) {
            spec = spec.and(
                    AnuncioJpaSpecification.aceitaDividirQuarto(
                            anuncioFiltroDTO.getAceitaDividirQuarto()
                    )
            );
        }
        List<AnuncioJpaEntity> anunciosJpa =
                anuncioRepository.findAll(spec);

        return anunciosJpa.stream()
                .map(anuncioMapper::JpaEntitytoModel)
                .toList();
    }


}