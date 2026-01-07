package com.roomie.app.feature.notifications.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.feature.match.data.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val repository: MatchRepository = MatchRepository()
) : ViewModel() {
    private val _state = MutableStateFlow(NotificationsState())
    val state: StateFlow<NotificationsState> = _state.asStateFlow()

    private val pageSize = 10

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        val userId = AuthSession.userId
        if (userId == null) {
            _state.value = _state.value.copy(
                error = "Usuário não autenticado"
            )
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            loadPage(page = 0, userId = userId, isInitialLoad = true)
        }
    }

    private suspend fun loadPage(page: Int, userId: Long, isInitialLoad: Boolean = false) {
        val result = repository.visualizarMeusLikes(
            page = page,
            size = pageSize,
            idOfertante = userId
        )

        result.fold(
            onSuccess = { response ->
                val currentState = _state.value
                val updatedMatches = if (isInitialLoad) {
                    response.content
                } else {
                    currentState.matches + response.content
                }
                _state.value = currentState.copy(
                    matches = updatedMatches,
                    isLoading = false,
                    isLoadingMore = false,
                    currentPage = page,
                    isLastPage = response.last,
                    error = null
                )
            },
            onFailure = { exception ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    isLoadingMore = false,
                    error = exception.message ?: "Erro ao carregar notificações"
                )
            }
        )
    }

    private fun loadMoreIfNeeded() {
        val currentState = _state.value
        if (currentState.shouldLoadMore) {
            val userId = AuthSession.userId
            if (userId != null) {
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoadingMore = true)
                    loadPage(
                        page = currentState.currentPage + 1,
                        userId = userId,
                        isInitialLoad = false
                    )
                }
            }
        }
    }

    fun onEvent(e: NotificationsEvent) {
        when (e) {
            NotificationsEvent.LoadInitial -> {
                loadInitialData()
            }
            NotificationsEvent.LoadMore -> {
                loadMoreIfNeeded()
            }
            is NotificationsEvent.ToggleExpand -> {
                val currentExpanded = _state.value.expandedMatchId
                _state.value = _state.value.copy(
                    expandedMatchId = if (currentExpanded == e.matchId) null else e.matchId
                )
            }
            is NotificationsEvent.AcceptMatch -> {
                // TODO: Implementar aceitar match
            }
            is NotificationsEvent.RejectMatch -> {
                // TODO: Implementar recusar match
            }
            NotificationsEvent.Refresh -> {
                loadInitialData()
            }
        }
    }
}

