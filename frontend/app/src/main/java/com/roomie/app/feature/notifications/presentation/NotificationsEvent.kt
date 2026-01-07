package com.roomie.app.feature.notifications.presentation

sealed interface NotificationsEvent {
    data object LoadInitial : NotificationsEvent
    data object LoadMore : NotificationsEvent
    data class ToggleExpand(val matchId: Long) : NotificationsEvent
    data class AcceptMatch(val matchId: Long) : NotificationsEvent
    data class RejectMatch(val matchId: Long) : NotificationsEvent
    data object Refresh : NotificationsEvent
}

