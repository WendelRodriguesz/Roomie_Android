package com.roomie.app.feature.chat.presentation

sealed interface ChatUserDetailEvent {
    data object LoadUserDetails : ChatUserDetailEvent
    data object ClearError : ChatUserDetailEvent
}

