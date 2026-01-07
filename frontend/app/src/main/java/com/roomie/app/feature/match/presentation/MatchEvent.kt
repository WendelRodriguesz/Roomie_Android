package com.roomie.app.feature.match.presentation

sealed interface MatchEvent {
    data object Like : MatchEvent
    data object Dislike : MatchEvent
    data object Save : MatchEvent
    data object Next : MatchEvent
    data object Prev : MatchEvent
    data object SeeMore : MatchEvent
    data object Refresh : MatchEvent
    data object DismissMatchSuccess : MatchEvent
}
