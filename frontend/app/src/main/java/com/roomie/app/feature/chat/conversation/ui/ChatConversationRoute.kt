package com.roomie.app.feature.chat.conversation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.feature.chat.conversation.presentation.ChatConversationViewModel
import com.roomie.app.feature.chat.conversation.presentation.ChatConversationViewModelFactory

@Composable
fun ChatConversationRoute(
    chatId: Long,
    otherUserId: Long,
    otherUserName: String,
    otherUserPhotoUrl: String?,
    onNavigateBack: () -> Unit,
    viewModel: ChatConversationViewModel = viewModel(
        factory = ChatConversationViewModelFactory(
            chatId = chatId,
            otherUserId = otherUserId,
            otherUserName = otherUserName,
            otherUserPhotoUrl = otherUserPhotoUrl
        )
    )
) {
    val state by viewModel.state.collectAsState()

    ChatConversationScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack
    )
}

