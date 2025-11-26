package com.roomie.app.feature.home.model

import com.roomie.app.R
import com.roomie.app.feature.match.model.ListingCard
import com.roomie.app.feature.match.model.ListingStatus

object ApartamentosMock {
    val itens = listOf(
        ListingCard(
            id = "home-1",
            offerorUserId = "host-1",
            title = "Apartamento Minimalista",
            description = "Espaço iluminado com varanda gourmet e coworking 24h.",
            neighborhood = "Butantã",
            city = "São Paulo",
            totalRent = 1200.0,
            mediaAccounts = 230.0,
            numResidents = 2,
            vacanciesDisp = 1,
            bedrooms = 3,
            bathrooms = 2,
            areaInSquareMeters = 85,
            acceptPets = true,
            rules = "Sem festas após 22h",
            status = ListingStatus.ACTIVE,
            createdInMillis = System.currentTimeMillis(),
            rating = 4.8,
            tags = listOf("Wi-Fi", "Lavanderia", "Garagem", "Coworking"),
            localPhoto = R.drawable.match1
        ),
        ListingCard(
            id = "home-2",
            offerorUserId = "host-2",
            title = "Loft Criativo na Vila",
            description = "Loft decorado com quarto suspenso, perfeito para quem trabalha remotamente.",
            neighborhood = "Vila Madalena",
            city = "São Paulo",
            totalRent = 1500.0,
            mediaAccounts = 190.0,
            numResidents = 1,
            vacanciesDisp = 1,
            bedrooms = 2,
            bathrooms = 1,
            areaInSquareMeters = 65,
            acceptPets = true,
            rules = "Pets pequenos, silêncio às 22h",
            status = ListingStatus.ACTIVE,
            createdInMillis = System.currentTimeMillis(),
            rating = 4.9,
            tags = listOf("Wi-Fi", "Pet-friendly", "Terraço", "Coworking"),
            localPhoto = R.drawable.match2
        ),
        ListingCard(
            id = "home-3",
            offerorUserId = "host-3",
            title = "Apartamento Solar",
            description = "Próximo ao metrô com bicicletário e área fitness exclusiva.",
            neighborhood = "Pinheiros",
            city = "São Paulo",
            totalRent = 1300.0,
            mediaAccounts = 210.0,
            numResidents = 3,
            vacanciesDisp = 1,
            bedrooms = 4,
            bathrooms = 3,
            areaInSquareMeters = 95,
            acceptPets = false,
            rules = "Não fumantes",
            status = ListingStatus.ACTIVE,
            createdInMillis = System.currentTimeMillis(),
            rating = 4.6,
            tags = listOf("Academia", "Bicicletário", "Wi-Fi", "Portaria 24h"),
            localPhoto = R.drawable.match1
        )
    )
}


