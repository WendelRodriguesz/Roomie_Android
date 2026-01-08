package com.roomie.app.feature.vaga.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateListingViewModel : ViewModel() {
    private val _state = MutableStateFlow(CreateListingState())
    val state: StateFlow<CreateListingState> = _state.asStateFlow()

    fun onEvent(event: CreateListingEvent) {
        val currentState = _state.value
        when (event) {
            is CreateListingEvent.TituloChanged -> {
                _state.value = currentState.copy(
                    formData = currentState.formData.copy(titulo = event.value),
                    validationError = null
                )
            }
            is CreateListingEvent.DescricaoChanged -> {
                _state.value = currentState.copy(
                    formData = currentState.formData.copy(descricao = event.value),
                    validationError = null
                )
            }
            is CreateListingEvent.RuaChanged -> {
                _state.value = currentState.copy(
                    formData = currentState.formData.copy(rua = event.value),
                    validationError = null
                )
            }
            is CreateListingEvent.NumeroChanged -> {
                _state.value = currentState.copy(
                    formData = currentState.formData.copy(numero = event.value),
                    validationError = null
                )
            }
            is CreateListingEvent.BairroChanged -> {
                _state.value = currentState.copy(
                    formData = currentState.formData.copy(bairro = event.value),
                    validationError = null
                )
            }
            is CreateListingEvent.CidadeChanged -> {
                _state.value = currentState.copy(
                    formData = currentState.formData.copy(cidade = event.value),
                    validationError = null
                )
            }
            is CreateListingEvent.EstadoChanged -> {
                _state.value = currentState.copy(
                    formData = currentState.formData.copy(estado = event.value),
                    validationError = null
                )
            }
            is CreateListingEvent.ValorAluguelChanged -> {
                val valor = event.value.replace(",", ".").toDoubleOrNull()
                _state.value = currentState.copy(
                    formData = currentState.formData.copy(valorAluguel = valor),
                    validationError = null
                )
            }
            is CreateListingEvent.ValorContasChanged -> {
                val valor = event.value.replace(",", ".").toDoubleOrNull()
                _state.value = currentState.copy(
                    formData = currentState.formData.copy(valorContas = valor),
                    validationError = null
                )
            }
            is CreateListingEvent.VagasDisponiveisChanged -> {
                _state.value = currentState.copy(
                    formData = currentState.formData.copy(vagasDisponiveis = event.value),
                    validationError = null
                )
            }
            is CreateListingEvent.TipoImovelChanged -> {
                _state.value = currentState.copy(
                    formData = currentState.formData.copy(tipoImovel = event.value),
                    validationError = null
                )
            }
            is CreateListingEvent.ComodosChanged -> {
                _state.value = currentState.copy(
                    formData = currentState.formData.copy(comodos = event.value),
                    validationError = null
                )
            }
            is CreateListingEvent.ImagesSelected -> {
                val currentImages = currentState.selectedImages.toMutableList()
                val newImages = event.uris.filter { !currentImages.contains(it) }
                currentImages.addAll(newImages)
                _state.value = currentState.copy(selectedImages = currentImages.take(10))
            }
            is CreateListingEvent.ImageRemoved -> {
                val updatedImages = currentState.selectedImages.toMutableList()
                if (event.index in updatedImages.indices) {
                    updatedImages.removeAt(event.index)
                    _state.value = currentState.copy(selectedImages = updatedImages)
                }
            }
            is CreateListingEvent.Submit -> {
                if (currentState.isValid) {
                    _state.value = CreateListingState()
                } else {
                    _state.value = currentState.copy(
                        validationError = "Por favor, preencha todos os campos obrigatórios"
                    )
                }
            }
            is CreateListingEvent.ClearError -> {
                _state.value = currentState.copy(errorMessage = null)
            }
            is CreateListingEvent.ClearValidationError -> {
                _state.value = currentState.copy(validationError = null)
            }
        }
    }
}

