package com.roomie.app.feature.chat.conversation.presentation

sealed interface ChatConversationEvent {
    data class MessageTextChanged(val text: String) : ChatConversationEvent
    data object SendMessage : ChatConversationEvent
    data object LoadHistory : ChatConversationEvent
    data object RetryConnection : ChatConversationEvent
    data object ClearError : ChatConversationEvent
}

