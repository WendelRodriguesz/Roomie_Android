package com.roomie.app.feature.home.presentation

sealed interface HomeEvent {
    data class SearchQueryChanged(val value: String) : HomeEvent
    data object Refresh : HomeEvent
    data object FiltersClicked : HomeEvent
}

