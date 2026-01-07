package com.roomie.app.feature.match.data.model

import com.roomie.app.feature.match.model.ListingCard
import com.roomie.app.feature.match.model.ListingStatus

fun MatchCandidate.toListingCard(): ListingCard {
    val anuncio = this.anuncio
    val totalRent = anuncio.valorAluguel + anuncio.valorContas
    
    // Criar tags baseadas nos dados disponíveis
    val tags = mutableListOf<String>()
    
    // Adicionar tipo de imóvel
    if (anuncio.tipoImovel.isNotBlank()) {
        tags.add(anuncio.tipoImovel.lowercase().replaceFirstChar { it.uppercase() })
    }
    
    // Adicionar vagas disponíveis
    if (anuncio.vagasDisponiveis > 0) {
        tags.add("${anuncio.vagasDisponiveis} vaga${if (anuncio.vagasDisponiveis > 1) "s" else ""}")
    }
    
    // Adicionar se aceita pets
    if (interesses.aceitaPets) {
        tags.add("Aceita pets")
    }
    
    // Adicionar cômodos (limitando para não ficar muito longo)
    anuncio.comodos.take(3).forEach { comodo ->
        val comodoFormatado = comodo.lowercase()
            .replace("_", " ")
            .replaceFirstChar { it.uppercase() }
        if (comodoFormatado.isNotBlank() && !tags.contains(comodoFormatado)) {
            tags.add(comodoFormatado)
        }
    }
    
    // Garantir que temos pelo menos algumas tags
    if (tags.isEmpty()) {
        tags.add("Disponível")
    }
    
    return ListingCard(
        id = anuncio.id.toString(),
        offerorUserId = this.id.toString(),
        title = anuncio.titulo.ifBlank { "Anúncio sem título" },
        description = anuncio.descricao.ifBlank { "Sem descrição disponível" },
        neighborhood = anuncio.bairro.ifBlank { "Bairro não informado" },
        city = anuncio.cidade.ifBlank { "Cidade não informada" },
        totalRent = totalRent,
        mediaAccounts = anuncio.valorContas,
        numResidents = 0, // Não disponível na API
        vacanciesDisp = anuncio.vagasDisponiveis,
        bedrooms = anuncio.comodos.count { it.contains("QUARTO", ignoreCase = true) },
        bathrooms = anuncio.comodos.count { it.contains("BANHEIRO", ignoreCase = true) },
        areaInSquareMeters = null, // Não disponível na API
        acceptPets = interesses.aceitaPets,
        rules = "", // Não disponível na API
        status = ListingStatus.ACTIVE,
        createdInMillis = System.currentTimeMillis(),
        rating = 0.0, // Não disponível na API
        tags = tags.take(5), // Limitar a 5 tags
        photos = anuncio.fotos ?: emptyList() // Garantir que não seja null
    )
}

