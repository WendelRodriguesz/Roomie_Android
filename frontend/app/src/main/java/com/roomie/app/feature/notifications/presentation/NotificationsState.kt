package com.roomie.app.feature.notifications.presentation

import com.roomie.app.feature.notifications.data.model.Match

data class NotificationsState(
    val matches: List<Match> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 0,
    val isLastPage: Boolean = false,
    val expandedMatchId: Long? = null
) {
    val shouldLoadMore: Boolean get() = 
        !isLastPage && !isLoadingMore && matches.isNotEmpty()
}

