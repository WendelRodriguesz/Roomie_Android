package com.roomie.app.feature.vaga.presentation

import android.net.Uri
import com.roomie.app.feature.vaga.model.TipoComodo
import com.roomie.app.feature.vaga.model.TipoImovel

sealed interface CreateListingEvent {
    data class TituloChanged(val value: String) : CreateListingEvent
    data class DescricaoChanged(val value: String) : CreateListingEvent
    data class RuaChanged(val value: String) : CreateListingEvent
    data class NumeroChanged(val value: String) : CreateListingEvent
    data class BairroChanged(val value: String) : CreateListingEvent
    data class CidadeChanged(val value: String) : CreateListingEvent
    data class EstadoChanged(val value: String) : CreateListingEvent
    data class ValorAluguelChanged(val value: String) : CreateListingEvent
    data class ValorContasChanged(val value: String) : CreateListingEvent
    data class VagasDisponiveisChanged(val value: Int) : CreateListingEvent
    data class TipoImovelChanged(val value: TipoImovel) : CreateListingEvent
    data class ComodosChanged(val value: List<TipoComodo>) : CreateListingEvent
    data class ImagesSelected(val uris: List<Uri>) : CreateListingEvent
    data class ImageRemoved(val index: Int) : CreateListingEvent
    data object Submit : CreateListingEvent
    data object ClearError : CreateListingEvent
    data object ClearValidationError : CreateListingEvent
}

