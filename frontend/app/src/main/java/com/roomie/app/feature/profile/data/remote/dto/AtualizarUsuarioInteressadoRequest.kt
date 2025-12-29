package com.roomie.app.feature.profile.data.remote.dto

data class InteressesUpdateDto(
    val frequencia_festas: String,
    val habitos_limpeza: String,
    val aceita_pets: Boolean,
    val horario_sono: String,
    val orcamento_min: Double?,
    val orcamento_max: Double?,
    val aceita_dividir_quarto: Boolean,
)

data class AtualizarUsuarioInteressadoRequest(
    val nome: String,
    val email: String?,
    val cidade: String,
    val ocupacao: String?,
    val bio: String?,
    val genero: String?,
    val foto_de_perfil: String?,
    val interesses: InteressesUpdateDto,
)
