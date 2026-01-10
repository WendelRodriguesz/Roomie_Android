package com.roomie.app.feature.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatUserDetailViewModelFactory(
    private val userId: Long,
    private val isOfertante: Boolean
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatUserDetailViewModel::class.java)) {
            return ChatUserDetailViewModel(userId, isOfertante) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

