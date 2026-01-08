package com.roomie.app.feature.home.data.remote.dto

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
        tags = emptyList(),
        localPhoto = null,
        photos = fotos
    )
}

