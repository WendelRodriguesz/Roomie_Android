package com.roomie.app.feature.match.presentation

import androidx.lifecycle.ViewModel
import com.roomie.app.feature.match.model.MatchMock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MatchViewModel : ViewModel() {
    private val _state = MutableStateFlow(MatchState(items = MatchMock.items))
    val state: StateFlow<MatchState> = _state.asStateFlow()

    fun onEvent(e: MatchEvent) {
        val s = _state.value
        when (e) {
            MatchEvent.Like,
            MatchEvent.Dislike,
            MatchEvent.Save,
            MatchEvent.Next -> _state.value = s.copy(index = (s.index + 1).coerceAtMost(s.items.lastIndex))
            MatchEvent.Prev -> _state.value = s.copy(index = (s.index - 1).coerceAtLeast(0))
            MatchEvent.SeeMore -> { /* navegar p/ detalhes - depois */ }
            MatchEvent.Refresh -> _state.value = MatchState(items = MatchMock.items)
        }
    }
}
