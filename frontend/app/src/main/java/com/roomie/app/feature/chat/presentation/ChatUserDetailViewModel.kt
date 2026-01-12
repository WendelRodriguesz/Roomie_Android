package com.roomie.app.feature.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomie.app.feature.chat.data.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatUserDetailViewModel(
    private val userId: Long,
    private val isOfertante: Boolean,
    private val repository: ChatRepository = ChatRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(ChatUserDetailState())
    val state: StateFlow<ChatUserDetailState> = _state.asStateFlow()

    init {
        loadUserDetails()
    }

    fun onEvent(event: ChatUserDetailEvent) {
        when (event) {
            ChatUserDetailEvent.LoadUserDetails -> loadUserDetails()
            ChatUserDetailEvent.ClearError -> _state.value = _state.value.copy(errorMessage = null)
        }
    }

    private fun loadUserDetails() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            val result = repository.carregarDetalhesUsuario(userId, isOfertante)
            result.fold(
                onSuccess = { userDetail ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        userDetail = userDetail,
                        errorMessage = null
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        userDetail = null,
                        errorMessage = error.message ?: "Erro ao carregar detalhes do usuário. Tente novamente."
                    )
                }
            )
        }
    }
}

