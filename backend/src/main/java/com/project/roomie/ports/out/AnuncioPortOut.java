package com.project.roomie.ports.out;

import com.project.roomie.core.model.Anuncio;
import com.project.roomie.infra.persistence.entity.AnuncioJpaEntity;

public interface AnuncioPortOut {
    Anuncio save(AnuncioJpaEntity anuncioJpaEntity);
}