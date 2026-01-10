package com.roomie.app.feature.chat.conversation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatConversationViewModelFactory(
    private val chatId: Long,
    private val otherUserId: Long,
    private val otherUserName: String,
    private val otherUserPhotoUrl: String?
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatConversationViewModel::class.java)) {
            return ChatConversationViewModel(
                chatId = chatId,
                otherUserId = otherUserId,
                otherUserName = otherUserName,
                otherUserPhotoUrl = otherUserPhotoUrl
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}