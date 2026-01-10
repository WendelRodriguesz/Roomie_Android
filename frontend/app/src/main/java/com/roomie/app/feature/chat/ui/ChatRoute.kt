package com.roomie.app.feature.chat.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.feature.chat.presentation.ChatEvent
import com.roomie.app.feature.chat.presentation.ChatViewModel
import com.roomie.app.feature.chat.presentation.ChatUserDetailEvent
import com.roomie.app.feature.chat.presentation.ChatUserDetailViewModel
import com.roomie.app.feature.chat.presentation.ChatUserDetailViewModelFactory

@Composable
fun ChatRoute(
    onChatClick: (Long) -> Unit = {},
    onViewUserDetails: (Long, Boolean) -> Unit = { _, _ -> },
    viewModel: ChatViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    ChatScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onChatClick = onChatClick,
        onViewUserDetails = onViewUserDetails
    )
}

@Composable
fun ChatUserDetailRoute(
    userId: Long,
    isOfertante: Boolean,
    onNavigateBack: () -> Unit,
    viewModel: ChatUserDetailViewModel = viewModel(
        factory = ChatUserDetailViewModelFactory(userId, isOfertante)
    )
) {
    val state by viewModel.state.collectAsState()

    ChatUserDetailScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack
    )
}

