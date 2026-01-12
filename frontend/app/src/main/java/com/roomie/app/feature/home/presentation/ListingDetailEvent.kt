package com.roomie.app.feature.home.presentation

sealed interface ListingDetailEvent {
    data object Refresh : ListingDetailEvent
    data object ShowInterest : ListingDetailEvent
    data object ClearInterestStatus : ListingDetailEvent
}

