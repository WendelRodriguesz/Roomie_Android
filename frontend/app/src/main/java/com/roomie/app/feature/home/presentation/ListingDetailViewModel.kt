package com.roomie.app.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.feature.home.data.HomeRepository
import com.roomie.app.feature.match.data.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListingDetailViewModel(
    private val repository: HomeRepository = HomeRepository(),
    private val matchRepository: MatchRepository = MatchRepository(),
    private val listingId: Int
) : ViewModel() {

    private val _state = MutableStateFlow(ListingDetailState(isLoading = true))
    val state: StateFlow<ListingDetailState> = _state.asStateFlow()

    init {
        carregarDetalhes()
    }

    fun onEvent(event: ListingDetailEvent) {
        when (event) {
            ListingDetailEvent.Refresh -> carregarDetalhes()
            ListingDetailEvent.ShowInterest -> enviarInteresse()
            ListingDetailEvent.ClearInterestStatus -> {
                _state.value = _state.value.copy(
                    interestSent = false,
                    interestError = null
                )
            }
        }
    }

    private fun carregarDetalhes() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            val result = repository.buscarAnuncioPorId(listingId)
            result.fold(
                onSuccess = { listing ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        listing = listing,
                        error = null
                    )
                },
                onFailure = { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        listing = null,
                        error = exception.message ?: "Erro ao carregar detalhes do anúncio"
                    )
                }
            )
        }
    }

    private fun enviarInteresse() {
        val listing = _state.value.listing
        val userId = AuthSession.userId
        val ofertanteId = listing?.idOfertante

        if (userId == null) {
            _state.value = _state.value.copy(
                interestError = "Usuário não autenticado"
            )
            return
        }

        if (ofertanteId == null) {
            _state.value = _state.value.copy(
                interestError = "Não foi possível identificar o ofertante"
            )
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(
                isSendingInterest = true,
                interestError = null,
                interestSent = false
            )

            val result = matchRepository.enviarLike(userId, ofertanteId.toLong())

            result.fold(
                onSuccess = {
                    _state.value = _state.value.copy(
                        isSendingInterest = false,
                        interestSent = true,
                        interestError = null
                    )
                },
                onFailure = { exception ->
                    _state.value = _state.value.copy(
                        isSendingInterest = false,
                        interestSent = false,
                        interestError = exception.message ?: "Erro ao demonstrar interesse"
                    )
                }
            )
        }
    }
}

class ListingDetailViewModelFactory(
    private val listingId: Int
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListingDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListingDetailViewModel(listingId = listingId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

