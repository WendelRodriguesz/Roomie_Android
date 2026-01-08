package com.roomie.app.feature.vaga.model

enum class TipoImovel(val label: String, val apiValue: String) {
    APARTAMENTO("Apartamento", "APARTAMENTO"),
    CASA("Casa", "CASA"),
}

enum class TipoComodo(val label: String, val apiValue: String) {
    SALA_DE_ESTAR("Sala de estar", "SALA_DE_ESTAR"),
    SALA_DE_JANTAR("Sala de jantar", "SALA_DE_JANTAR"),
    COZINHA("Cozinha", "COZINHA"),
    BANHEIRO("Banheiro", "BANHEIRO"),
    QUARTO("Quarto", "QUARTO"),
    LAVANDERIA("Lavanderia", "LAVANDERIA"),
    GARAGEM("Garagem", "GARAGEM"),
    VARANDA("Varanda", "VARANDA")
}

data class ListingFormData(
    val titulo: String = "",
    val descricao: String = "",
    val rua: String = "",
    val numero: String = "",
    val bairro: String = "",
    val cidade: String = "",
    val estado: String = "",
    val valorAluguel: Double? = null,
    val valorContas: Double? = null,
    val vagasDisponiveis: Int = 1,
    val tipoImovel: TipoImovel? = null,
    val comodos: List<TipoComodo> = emptyList()
)

