package com.roomie.app.feature.home.model

data class ListingDetail(
    val id: Int,
    val titulo: String,
    val descricao: String,
    val rua: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val valorAluguel: Double,
    val valorContas: Double,
    val vagasDisponiveis: Int,
    val tipoImovel: String,
    val fotos: List<String>,
    val comodos: List<String>,
    val statusAnuncio: String?
)

