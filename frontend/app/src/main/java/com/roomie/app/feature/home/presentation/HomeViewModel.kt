package com.roomie.app.feature.home.presentation

import androidx.lifecycle.ViewModel
import com.roomie.app.feature.home.model.ApartamentosMock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val catalog = ApartamentosMock.itens

    private val _state = MutableStateFlow(HomeState(listings = catalog))
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Refresh -> _state.value = _state.value.copy(
                searchQuery = "",
                listings = catalog
            )
            is HomeEvent.SearchQueryChanged -> filter(event.value)
            HomeEvent.FiltersClicked -> _state.value = _state.value.copy(
                isFiltering = !_state.value.isFiltering
            )
        }
    }

    private fun filter(query: String) {
        val normalized = query.trim()
        val filtered = if (normalized.isEmpty()) {
            catalog
        } else {
            catalog.filter { listing ->
                listing.title.contains(normalized, ignoreCase = true) ||
                        listing.neighborhood.contains(normalized, ignoreCase = true) ||
                        listing.city.contains(normalized, ignoreCase = true)
            }
        }
        _state.value = _state.value.copy(searchQuery = query, listings = filtered)
    }
}

