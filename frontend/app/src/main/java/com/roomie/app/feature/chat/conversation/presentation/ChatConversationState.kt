package com.roomie.app.feature.chat.conversation.presentation

import com.roomie.app.core.data.websocket.StompWebSocketManager
import com.roomie.app.feature.chat.model.Mensagem

data class ChatConversationState(
    val chatId: Long,
    val otherUserId: Long,
    val otherUserName: String,
    val otherUserPhotoUrl: String?,
    val isLoading: Boolean = false,
    val isLoadingHistory: Boolean = false,
    val mensagens: List<Mensagem> = emptyList(),
    val connectionState: StompWebSocketManager.ConnectionState = StompWebSocketManager.ConnectionState.Disconnected,
    val errorMessage: String? = null,
    val messageText: String = ""
)

