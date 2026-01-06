package com.project.roomie.adapters.out;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.infra.persistence.entity.AnuncioJpaEntity;
import com.project.roomie.infra.persistence.repository.AnuncioRepository;
import com.project.roomie.mapper.AnuncioMapper;
import com.project.roomie.ports.out.AnuncioPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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


}