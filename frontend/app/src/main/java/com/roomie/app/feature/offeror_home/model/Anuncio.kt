package com.roomie.app.feature.offeror_home.model

enum class StatusAnuncio {
    ATIVO, PAUSADO, INATIVO
}

data class Anuncio(
    val id: Long,
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
    val status: StatusAnuncio
) {
    val enderecoCompleto: String
        get() = "$rua, $numero - $bairro, $cidade - $estado"
    
    val valorTotal: Double
        get() = valorAluguel + valorContas
    
    val isAtivo: Boolean
        get() = status == StatusAnuncio.ATIVO
}
