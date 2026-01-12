package com.roomie.app.feature.offeror_home.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CadastrarAnuncioRequest(
    val titulo: String,
    val descricao: String,
    val rua: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    @SerializedName("valor_aluguel")
    val valorAluguel: Double,
    @SerializedName("valor_contas")
    val valorContas: Double,
    @SerializedName("vagas_disponiveis")
    val vagasDisponiveis: Int,
    val tipo_imovel: String,
    val comodos: List<String>
)