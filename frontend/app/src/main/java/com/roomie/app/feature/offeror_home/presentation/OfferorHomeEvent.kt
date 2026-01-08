package com.roomie.app.feature.offeror_home.presentation

sealed interface OfferorHomeEvent {
    data object LoadAnuncio : OfferorHomeEvent
    data object PausarAnuncio : OfferorHomeEvent
    data object ReativarAnuncio : OfferorHomeEvent
    data object EditarAnuncio : OfferorHomeEvent
    data object DismissError : OfferorHomeEvent
    data object DismissSuccess : OfferorHomeEvent
}
