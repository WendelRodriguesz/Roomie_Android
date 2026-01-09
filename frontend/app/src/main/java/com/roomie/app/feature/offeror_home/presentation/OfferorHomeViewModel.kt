package com.roomie.app.feature.offeror_home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomie.app.feature.offeror_home.data.AnuncioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OfferorHomeViewModel(
    private val repository: AnuncioRepository,
    private val anuncioId: Long,
    private val token: String
) : ViewModel() {

    private val _state = MutableStateFlow(OfferorHomeState())
    val state: StateFlow<OfferorHomeState> = _state.asStateFlow()

    init {
        loadAnuncio()
    }

    fun onEvent(event: OfferorHomeEvent) {
        when (event) {
            OfferorHomeEvent.LoadAnuncio -> loadAnuncio()
            OfferorHomeEvent.PausarAnuncio -> pausarAnuncio()
            OfferorHomeEvent.ReativarAnuncio -> reativarAnuncio()
            OfferorHomeEvent.EditarAnuncio -> {
            }
            OfferorHomeEvent.DismissError -> {
                _state.value = _state.value.copy(errorMessage = null)
            }
            OfferorHomeEvent.DismissSuccess -> {
                _state.value = _state.value.copy(successMessage = null)
            }
        }
    }

    private fun loadAnuncio() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            
            repository.visualizarAnuncio(anuncioId, token)
                .onSuccess { anuncio ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        anuncio = anuncio,
                        errorMessage = null
                    )
                }
                .onFailure { exception ->
                    val errorMsg = exception.message ?: "Erro desconhecido ao carregar anúncio"
                    android.util.Log.e("OfferorHomeViewModel", "Erro ao carregar anúncio", exception)
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = errorMsg
                    )
                }
        }
    }

    private fun pausarAnuncio() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null, successMessage = null)
            
            repository.pausarAnuncio(anuncioId, token)
                .onSuccess { anuncio ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        anuncio = anuncio,
                        successMessage = "Anúncio pausado com sucesso"
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Erro ao pausar anúncio"
                    )
                }
        }
    }

    private fun reativarAnuncio() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null, successMessage = null)
            
            repository.reativarAnuncio(anuncioId, token)
                .onSuccess { anuncio ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        anuncio = anuncio,
                        successMessage = "Anúncio reativado com sucesso"
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Erro ao reativar anúncio"
                    )
                }
        }
    }
}
