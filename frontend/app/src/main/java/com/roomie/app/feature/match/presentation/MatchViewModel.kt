package com.roomie.app.feature.match.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.feature.match.data.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MatchViewModel(
    private val repository: MatchRepository = MatchRepository()
) : ViewModel() {
    private val _state = MutableStateFlow(MatchState())
    val state: StateFlow<MatchState> = _state.asStateFlow()

    private val pageSize = 5

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
        val result = repository.buscarCandidatosAsListingCards(
            page = page,
            size = pageSize,
            idUsuarioInteressado = userId
        )

        result.fold(
            onSuccess = { (newItems, isLastPage) ->
                val currentState = _state.value
                val updatedItems = if (isInitialLoad) {
                    newItems
                } else {
                    currentState.items + newItems
                }
                _state.value = currentState.copy(
                    items = updatedItems,
                    isLoading = false,
                    isLoadingMore = false,
                    currentPage = page,
                    isLastPage = isLastPage,
                    error = null
                )
            },
            onFailure = { exception ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    isLoadingMore = false,
                    error = exception.message ?: "Erro ao carregar candidatos"
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

    fun onEvent(e: MatchEvent) {
        val s = _state.value
        when (e) {
            MatchEvent.Like -> {
                val currentItem = s.current
                if (currentItem != null) {
                    val userId = AuthSession.userId
                    val offerorUserId = currentItem.offerorUserId.toLongOrNull()
                    
                    if (userId != null && offerorUserId != null) {
                        viewModelScope.launch {
                            val result = repository.enviarLike(userId, offerorUserId)
                            
                            result.fold(
                                onSuccess = {
                                    val updatedItems = s.items.filter { it.id != currentItem.id }
                                    val newIndex = if (s.index >= updatedItems.size) {
                                        (updatedItems.size - 1).coerceAtLeast(0)
                                    } else {
                                        s.index
                                    }
                                    
                                    _state.value = s.copy(
                                        items = updatedItems,
                                        index = newIndex,
                                        showMatchSuccess = true
                                    )
                                    
                                    if (newIndex == updatedItems.lastIndex && !s.isLastPage) {
                                        loadMoreIfNeeded()
                                    }
                                },
                                onFailure = { exception ->
                                    _state.value = s.copy(
                                        error = exception.message ?: "Erro ao enviar like"
                                    )
                                }
                            )
                        }
                    }
                }
            }
            MatchEvent.DismissMatchSuccess -> {
                _state.value = s.copy(showMatchSuccess = false)
            }
            MatchEvent.Save -> {
                val newIndex = (s.index + 1).coerceAtMost(s.items.lastIndex)
                _state.value = s.copy(index = newIndex)
                if (newIndex == s.items.lastIndex) {
                    loadMoreIfNeeded()
                }
            }
            MatchEvent.Next -> {
                val newIndex = (s.index + 1).coerceAtMost(s.items.lastIndex)
                _state.value = s.copy(index = newIndex)
                if (newIndex == s.items.lastIndex) {
                    loadMoreIfNeeded()
                }
            }
            MatchEvent.Prev -> {
                _state.value = s.copy(index = (s.index - 1).coerceAtLeast(0))
            }
            MatchEvent.SeeMore -> { /* navegar p/ detalhes - depois */ }
            MatchEvent.Refresh -> {
                loadInitialData()
            }

            MatchEvent.Dislike -> TODO()
        }
    }
}
