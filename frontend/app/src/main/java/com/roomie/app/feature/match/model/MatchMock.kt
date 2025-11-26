package com.roomie.app.feature.match.model

object MatchMock {
    val items = listOf(
        ListingCard(
            id = "1",
            offerorUserId = "u1",
            title = "Apartamento Moderno\nVila Madalena",
            description = "Apto de 2 quartos em localização privilegiada.",
            neighborhood = "Vila Madalena",
            city = "São Paulo",
            totalRent = 1200.0,
            mediaAccounts = 200.0,
            numResidents = 2,
            vacanciesDisp = 1,
            bedrooms = 2,
            bathrooms = 2,
            areaInSquareMeters = 78,
            acceptPets = true,
            rules = "Sem festas após 22h",
            status = ListingStatus.ACTIVE,
            createdInMillis = System.currentTimeMillis(),
            rating = 4.8,
            tags = listOf("2 quartos", "Piscina", "Academia"),
            photos = listOf("match_1")
        ),
        ListingCard(
            id = "2",
            offerorUserId = "u2",
            title = "Studio Compacto",
            description = "Studio mobiliado perto do metrô.",
            neighborhood = "Centro",
            city = "Fortaleza",
            totalRent = 980.0,
            mediaAccounts = 180.0,
            numResidents = 1,
            vacanciesDisp = 1,
            bedrooms = 1,
            bathrooms = 1,
            areaInSquareMeters = 42,
            acceptPets = false,
            rules = "Proibido fumar",
            status = ListingStatus.ACTIVE,
            createdInMillis = System.currentTimeMillis(),
            rating = 4.6,
            tags = listOf("Mobiliado", "Pet friendly?", "Garagem"),
            photos = listOf("match_2")
        )
    )
}
