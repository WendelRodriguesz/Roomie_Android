package com.roomie.app.feature.home.data.remote.dto

import com.roomie.app.feature.home.model.ListingDetail
import com.roomie.app.feature.match.model.ListingCard
import com.roomie.app.feature.match.model.ListingStatus

fun AnuncioResponseDto.toListingCard(): ListingCard {
    val status = when (statusAnuncio) {
        "ATIVO" -> ListingStatus.ACTIVE
        "PAUSADO" -> ListingStatus.INACTIVE
        null -> ListingStatus.ACTIVE
        else -> ListingStatus.ACTIVE
    }

    return ListingCard(
        id = id.toString(),
        offerorUserId = "",
        title = titulo,
        description = descricao,
        neighborhood = bairro,
        city = cidade,
        totalRent = valor_aluguel,
        mediaAccounts = valor_contas,
        numResidents = 0,
        vacanciesDisp = vagas_disponiveis,
        bedrooms = 0,
        bathrooms = 0,
        areaInSquareMeters = null,
        acceptPets = false,
        rules = "",
        status = status,
        createdInMillis = 0L,
        rating = 0.0,
        tags = comodos,
        localPhoto = null,
        photos = fotos
    )
}

fun AnuncioResponseDto.toListingDetail(): ListingDetail {
    return ListingDetail(
        id = id,
        titulo = titulo,
        descricao = descricao,
        rua = rua,
        numero = numero,
        bairro = bairro,
        cidade = cidade,
        estado = estado,
        valorAluguel = valor_aluguel,
        valorContas = valor_contas,
        vagasDisponiveis = vagas_disponiveis,
        tipoImovel = tipo_imovel,
        fotos = fotos,
        comodos = comodos,
        statusAnuncio = statusAnuncio
    )
}

