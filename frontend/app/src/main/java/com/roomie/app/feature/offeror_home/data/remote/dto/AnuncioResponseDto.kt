package com.roomie.app.feature.offeror_home.data.remote.dto

data class AnuncioResponseDto(
    val id: Long,
    val titulo: String,
    val descricao: String,
    val rua: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val valor_aluguel: Double,
    val valor_contas: Double,
    val vagas_disponiveis: Int,
    val tipo_imovel: String,
    val fotos: List<String>,
    val comodos: List<String>,
    val statusAnuncio: String? = null,
    val status_anuncio: String? = null,
    val id_usuario_ofertante: Long? = null
) {
    val status: String
        get() = statusAnuncio ?: status_anuncio ?: "ATIVO"
}
