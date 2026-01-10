package com.roomie.app.feature.home.presentation

import com.roomie.app.feature.home.model.ListingDetail

data class ListingDetailState(
    val isLoading: Boolean = false,
    val listing: ListingDetail? = null,
    val error: String? = null
)

