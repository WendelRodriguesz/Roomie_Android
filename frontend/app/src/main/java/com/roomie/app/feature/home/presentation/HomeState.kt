package com.roomie.app.feature.home.presentation

import com.roomie.app.feature.match.model.ListingCard

data class HomeState(
    val searchQuery: String = "",
    val listings: List<ListingCard> = emptyList(),
    val isFiltering: Boolean = false,
    val filterCidade: String = "",
    val filterBairro: String = "",
    val filterCustoMin: String = "",
    val filterCustoMax: String = "",
    val filterTipoImovel: String? = null,
    val filterVagasMin: String = "",
    val filterAceitaPets: Boolean? = null,
    val filterAceitaDividirQuarto: Boolean? = null,
    val isLoading: Boolean = false
) {
    val hasResults: Boolean = listings.isNotEmpty()
    
    fun hasActiveFilters(): Boolean {
        return filterCidade.isNotBlank() ||
                filterBairro.isNotBlank() ||
                filterCustoMin.isNotBlank() ||
                filterCustoMax.isNotBlank() ||
                filterTipoImovel != null ||
                filterVagasMin.isNotBlank() ||
                filterAceitaPets != null ||
                filterAceitaDividirQuarto != null
    }
}

