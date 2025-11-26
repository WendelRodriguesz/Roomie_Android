package com.roomie.app.feature.home.presentation

import com.roomie.app.feature.match.model.ListingCard

data class HomeState(
    val searchQuery: String = "",
    val listings: List<ListingCard> = emptyList(),
    val favorites: Set<String> = emptySet(),
    val isFiltering: Boolean = false
) {
    val hasResults: Boolean = listings.isNotEmpty()
}

