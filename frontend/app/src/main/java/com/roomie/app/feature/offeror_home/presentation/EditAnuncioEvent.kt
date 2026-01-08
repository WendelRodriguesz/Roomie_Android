package com.roomie.app.feature.offeror_home.presentation

sealed interface EditAnuncioEvent {
    data class UpdateTitulo(val titulo: String) : EditAnuncioEvent
    data class UpdateDescricao(val descricao: String) : EditAnuncioEvent
    data class UpdateRua(val rua: String) : EditAnuncioEvent
    data class UpdateNumero(val numero: String) : EditAnuncioEvent
    data class UpdateBairro(val bairro: String) : EditAnuncioEvent
    data class UpdateCidade(val cidade: String) : EditAnuncioEvent
    data class UpdateEstado(val estado: String) : EditAnuncioEvent
    data class UpdateValorAluguel(val valor: String) : EditAnuncioEvent
    data class UpdateValorContas(val valor: String) : EditAnuncioEvent
    data class UpdateVagasDisponiveis(val vagas: String) : EditAnuncioEvent
    data class UpdateTipoImovel(val tipo: String) : EditAnuncioEvent
    data class UpdateComodos(val comodos: List<String>) : EditAnuncioEvent
    data object LoadAnuncio : EditAnuncioEvent
    data object SaveAnuncio : EditAnuncioEvent
    data object DismissError : EditAnuncioEvent
    data object DismissSuccess : EditAnuncioEvent
}
