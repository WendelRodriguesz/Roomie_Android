package com.roomie.app.feature.chat.model

data class ChatUserDetail(
    val id: Long,
    val nome: String,
    val email: String?,
    val dataDeNascimento: String?,
    val idade: Int?,
    val cidade: String,
    val ocupacao: String?,
    val bio: String?,
    val genero: String?,
    val fotoDePerfil: String?,
    val isOfertante: Boolean,
    val anuncio: AnuncioInfo?
)

data class AnuncioInfo(
    val id: Long?,
    val titulo: String?,
    val descricao: String?,
    val rua: String?,
    val numero: String?,
    val bairro: String?,
    val cidade: String?,
    val estado: String?,
    val valorAluguel: Float?,
    val valorContas: Float?,
    val vagasDisponiveis: Int?,
    val tipoImovel: String?,
    val fotos: List<String>?,
    val comodos: List<String>?
)

