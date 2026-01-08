package com.roomie.app.feature.offeror_home.data.remote.dto

data class AtualizarAnuncioRequest(
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
    val comodos: List<String>
)
