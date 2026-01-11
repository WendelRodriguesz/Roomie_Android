package com.roomie.app.feature.vaga.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.feature.offeror_home.data.AnuncioRepository
import com.roomie.app.feature.offeror_home.data.remote.dto.CadastrarAnuncioRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateListingViewModel(
    private val anuncioRepository: AnuncioRepository = AnuncioRepository(com.roomie.app.core.data.api.RetrofitClient.anuncioApiService)
) : ViewModel() {
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
                    val userId = AuthSession.userId
                    val token = AuthSession.token
                    
                    if (userId == null || token.isNullOrBlank()) {
                        _state.value = currentState.copy(
                            errorMessage = "Usuário não autenticado"
                        )
                        return
                    }
                    
                    _state.value = currentState.copy(isLoading = true, errorMessage = null)
                    
                    viewModelScope.launch {
                        try {
                            val request = CadastrarAnuncioRequest(
                                titulo = currentState.formData.titulo,
                                descricao = currentState.formData.descricao,
                                rua = currentState.formData.rua,
                                numero = currentState.formData.numero,
                                bairro = currentState.formData.bairro,
                                cidade = currentState.formData.cidade,
                                estado = currentState.formData.estado,
                                valorAluguel = currentState.formData.valorAluguel!!,
                                valorContas = currentState.formData.valorContas!!,
                                vagasDisponiveis = currentState.formData.vagasDisponiveis,
                                tipo_imovel = currentState.formData.tipoImovel!!.apiValue,
                                comodos = currentState.formData.comodos.map { it.apiValue }
                            )
                            
                            val result = anuncioRepository.cadastrarAnuncio(userId, token, request)
                            
                            result.fold(
                                onSuccess = { anuncio ->
                                    _state.value = currentState.copy(
                                        isLoading = false,
                                        createdAnuncioId = anuncio.id,
                                        isSubmitted = true
                                    )
                                },
                                onFailure = { exception ->
                                    _state.value = currentState.copy(
                                        isLoading = false,
                                        errorMessage = exception.message ?: "Erro ao cadastrar anúncio"
                                    )
                                }
                            )
                        } catch (e: Exception) {
                            _state.value = currentState.copy(
                                isLoading = false,
                                errorMessage = "Erro ao cadastrar anúncio: ${e.message}"
                            )
                        }
                    }
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

