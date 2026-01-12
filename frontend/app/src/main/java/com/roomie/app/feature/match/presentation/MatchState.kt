package com.roomie.app.feature.match.presentation

import com.roomie.app.feature.match.model.ListingCard

data class MatchState(
    val items: List<ListingCard> = emptyList(),
    val index: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 0,
    val isLastPage: Boolean = false,
    val isLoadingMore: Boolean = false,
    val showMatchSuccess: Boolean = false
) {
    val current: ListingCard? get() = items.getOrNull(index)
    val hasPrev: Boolean get() = index > 0
    val hasNext: Boolean get() = index < items.lastIndex
    
    val shouldLoadMore: Boolean get() = 
        index == items.lastIndex && !isLastPage && !isLoadingMore
}
