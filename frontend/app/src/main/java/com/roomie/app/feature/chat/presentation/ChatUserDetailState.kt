package com.roomie.app.feature.chat.presentation

import com.roomie.app.feature.chat.model.ChatUserDetail

data class ChatUserDetailState(
    val isLoading: Boolean = false,
    val userDetail: ChatUserDetail? = null,
    val errorMessage: String? = null
)

