package com.roomie.app.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomie.app.feature.home.data.HomeRepository
import com.roomie.app.feature.match.model.ListingCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository = HomeRepository()
) : ViewModel() {

    private var catalog: List<ListingCard> = emptyList()

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        carregarAnuncios()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Refresh -> carregarAnuncios()
            is HomeEvent.SearchQueryChanged -> filterLocal(event.value)
            HomeEvent.FiltersClicked -> _state.value = _state.value.copy(
                isFiltering = !_state.value.isFiltering
            )
            HomeEvent.CloseFilters -> _state.value = _state.value.copy(
                isFiltering = false
            )
            HomeEvent.ApplyFilters -> aplicarFiltros()
            HomeEvent.ClearFilters -> limparFiltros()
            is HomeEvent.FilterCidadeChanged -> _state.value = _state.value.copy(
                filterCidade = event.value
            )
            is HomeEvent.FilterBairroChanged -> _state.value = _state.value.copy(
                filterBairro = event.value
            )
            is HomeEvent.FilterCustoMinChanged -> _state.value = _state.value.copy(
                filterCustoMin = event.value
            )
            is HomeEvent.FilterCustoMaxChanged -> _state.value = _state.value.copy(
                filterCustoMax = event.value
            )
            is HomeEvent.FilterTipoImovelChanged -> _state.value = _state.value.copy(
                filterTipoImovel = event.value
            )
            is HomeEvent.FilterVagasMinChanged -> _state.value = _state.value.copy(
                filterVagasMin = event.value
            )
            is HomeEvent.FilterAceitaPetsChanged -> _state.value = _state.value.copy(
                filterAceitaPets = event.value
            )
            is HomeEvent.FilterAceitaDividirQuartoChanged -> _state.value = _state.value.copy(
                filterAceitaDividirQuarto = event.value
            )
        }
    }

    private fun carregarAnuncios() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = repository.listarAnuncios()
            result.fold(
                onSuccess = { listings ->
                    catalog = listings
                    _state.value = _state.value.copy(
                        searchQuery = "",
                        listings = listings,
                        isLoading = false
                    )
                },
                onFailure = {
                    catalog = emptyList()
                    _state.value = _state.value.copy(
                        searchQuery = "",
                        listings = emptyList(),
                        isLoading = false
                    )
                }
            )
        }
    }

    private fun filterLocal(query: String) {
        val normalized = query.trim()
        val base = catalog
        val filtered = if (normalized.isEmpty()) {
            base
        } else {
            base.filter { listing ->
                listing.title.contains(normalized, ignoreCase = true) ||
                        listing.neighborhood.contains(normalized, ignoreCase = true) ||
                        listing.city.contains(normalized, ignoreCase = true)
            }
        }
        _state.value = _state.value.copy(searchQuery = query, listings = filtered)
    }

    private fun aplicarFiltros() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, isFiltering = false)
            val result = repository.filtrarAnuncios(
                cidade = _state.value.filterCidade.takeIf { it.isNotBlank() },
                bairro = _state.value.filterBairro.takeIf { it.isNotBlank() },
                custoMin = _state.value.filterCustoMin.toFloatOrNull(),
                custoMax = _state.value.filterCustoMax.toFloatOrNull(),
                tipoImovel = _state.value.filterTipoImovel,
                vagasMin = _state.value.filterVagasMin.toIntOrNull(),
                aceitaPets = _state.value.filterAceitaPets,
                aceitaDividirQuarto = _state.value.filterAceitaDividirQuarto
            )
            result.fold(
                onSuccess = { listings ->
                    catalog = listings
                    _state.value = _state.value.copy(
                        listings = listings,
                        isLoading = false
                    )
                },
                onFailure = {
                    catalog = emptyList()
                    _state.value = _state.value.copy(
                        listings = emptyList(),
                        isLoading = false
                    )
                }
            )
        }
    }

    private fun limparFiltros() {
        _state.value = _state.value.copy(
            filterCidade = "",
            filterBairro = "",
            filterCustoMin = "",
            filterCustoMax = "",
            filterTipoImovel = null,
            filterVagasMin = "",
            filterAceitaPets = null,
            filterAceitaDividirQuarto = null
        )
        carregarAnuncios()
    }
}

