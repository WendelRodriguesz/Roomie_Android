package com.roomie.app.feature.profile.data.remote.dto

data class InteressesDto(
    val id: Long,
    val frequencia_festas: String,
    val habitos_limpeza: String,
    val aceita_pets: Boolean,
    val horario_sono: String,
    val orcamento_min: Double?,
    val orcamento_max: Double?,
    val aceita_dividir_quarto: Boolean,
)

data class UsuarioInteressadoDto(
    val id: Long,
    val nome: String,
    val email: String?,
    val data_de_nascimento: String?,
    val idade: Int?,
    val cidade: String,
    val ocupacao: String?,
    val bio: String?,
    val genero: String?,
    val foto_de_perfil: String?,
    val interesses: InteressesDto?,
)
