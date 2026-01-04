package com.roomie.app.feature.profile.data.remote.dto

data class UsuarioOfertanteDto(
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
    val interesses: InteressesOfertantesDto?,
    val anuncio: AnuncioDto?,
)

data class InteressesOfertantesDto(
    val id: Long?,
    val frequencia_festas: String?,
    val habitos_limpeza: String?,
    val aceita_pets: Boolean?,
    val horario_sono: String?,
    val aceita_dividir_quarto: Boolean?,
)

data class AnuncioDto(
    val id: Long?,
    val titulo: String?,
    val descricao: String?,
    val rua: String?,
    val numero: String?,
    val bairro: String?,
    val cidade: String?,
    val estado: String?,
    val valor_aluguel: Float?,
    val valor_contas: Float?,
    val vagas_disponiveis: Int?,
    val tipo_imovel: String?,
    val fotos: List<String>?,
    val comodos: List<String>?,
)
