package com.roomie.app.feature.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.feature.chat.data.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository = ChatRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    init {
        loadChats()
    }

    fun onEvent(event: ChatEvent) {
        when (event) {
            ChatEvent.LoadChats -> loadChats()
            ChatEvent.RefreshChats -> loadChats()
            ChatEvent.ClearError -> _state.value = _state.value.copy(errorMessage = null)
        }
    }

    private fun loadChats() {
        val userId = AuthSession.userId
        if (userId == null) {
            _state.value = _state.value.copy(
                isLoading = false,
                errorMessage = "Usuário não encontrado. Faça login novamente."
            )
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            val result = repository.carregarListaChats(userId)
            result.fold(
                onSuccess = { chats ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        chats = chats,
                        errorMessage = null
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        chats = emptyList(),
                        errorMessage = error.message ?: "Erro ao carregar chats. Tente novamente."
                    )
                }
            )
        }
    }
}

