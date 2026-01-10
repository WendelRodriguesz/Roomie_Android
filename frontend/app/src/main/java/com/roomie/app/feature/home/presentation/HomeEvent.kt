package com.roomie.app.feature.home.presentation

sealed interface HomeEvent {
    data class SearchQueryChanged(val value: String) : HomeEvent
    data object Refresh : HomeEvent
    data object FiltersClicked : HomeEvent
    data object CloseFilters : HomeEvent
    data object ApplyFilters : HomeEvent
    data object ClearFilters : HomeEvent
    data class FilterCidadeChanged(val value: String) : HomeEvent
    data class FilterBairroChanged(val value: String) : HomeEvent
    data class FilterCustoMinChanged(val value: String) : HomeEvent
    data class FilterCustoMaxChanged(val value: String) : HomeEvent
    data class FilterTipoImovelChanged(val value: String?) : HomeEvent
    data class FilterVagasMinChanged(val value: String) : HomeEvent
    data class FilterAceitaPetsChanged(val value: Boolean?) : HomeEvent
    data class FilterAceitaDividirQuartoChanged(val value: Boolean?) : HomeEvent
}

