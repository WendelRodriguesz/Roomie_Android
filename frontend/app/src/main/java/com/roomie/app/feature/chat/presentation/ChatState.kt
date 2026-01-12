package com.roomie.app.feature.chat.presentation

import com.roomie.app.feature.chat.model.ChatListItem

data class ChatState(
    val isLoading: Boolean = false,
    val chats: List<ChatListItem> = emptyList(),
    val errorMessage: String? = null
)

