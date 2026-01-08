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
            is HomeEvent.SearchQueryChanged -> filter(event.value)
            HomeEvent.FiltersClicked -> _state.value = _state.value.copy(
                isFiltering = !_state.value.isFiltering
            )
        }
    }

    private fun carregarAnuncios() {
        viewModelScope.launch {
            val result = repository.listarAnuncios()
            result.fold(
                onSuccess = { listings ->
                    catalog = listings
                    _state.value = _state.value.copy(
                        searchQuery = "",
                        listings = listings
                    )
                },
                onFailure = {
                    catalog = emptyList()
                    _state.value = _state.value.copy(
                        searchQuery = "",
                        listings = emptyList()
                    )
                }
            )
        }
    }

    private fun filter(query: String) {
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
}

