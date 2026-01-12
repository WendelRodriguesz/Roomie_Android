package com.roomie.app.feature.chat.presentation

sealed interface ChatEvent {
    data object LoadChats : ChatEvent
    data object RefreshChats : ChatEvent
    data object ClearError : ChatEvent
}

