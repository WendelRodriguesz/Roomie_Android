package com.roomie.app.feature.offeror_home.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AtualizarAnuncioRequest(
    val titulo: String,
    val descricao: String,
    val rua: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    @SerializedName("valorAluguel")
    val valorAluguel: Float,
    @SerializedName("valorContas")
    val valorContas: Float,
    @SerializedName("vagasDisponiveis")
    val vagasDisponiveis: Int,
    val tipo_imovel: String,
    val comodos: List<String>
)
