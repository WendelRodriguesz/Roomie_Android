package com.roomie.app.feature.vaga.presentation

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.feature.offeror_home.data.AnuncioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UploadImagesViewModel(
    private val anuncioRepository: AnuncioRepository = AnuncioRepository(com.roomie.app.core.data.api.RetrofitClient.anuncioApiService)
) : ViewModel() {
    
    private var context: Context? = null
    
    fun setContext(context: Context) {
        this.context = context
    }

    private val _state = MutableStateFlow(UploadImagesState())
    val state: StateFlow<UploadImagesState> = _state.asStateFlow()

    fun onEvent(event: UploadImagesEvent) {
        val currentState = _state.value
        when (event) {
            is UploadImagesEvent.ImageSelected -> {
                val currentImages = currentState.selectedImages.toMutableList()
                val newImages = event.uris.filter { !currentImages.contains(it) }
                currentImages.addAll(newImages)
                _state.value = currentState.copy(selectedImages = currentImages.take(6))
            }
            is UploadImagesEvent.ImageRemoved -> {
                val updatedImages = currentState.selectedImages.toMutableList()
                if (event.index in updatedImages.indices) {
                    updatedImages.removeAt(event.index)
                    _state.value = currentState.copy(selectedImages = updatedImages)
                }
            }
            is UploadImagesEvent.Submit -> {
                val userId = currentState.userId
                
                if (userId == null) {
                    _state.value = currentState.copy(
                        errorMessage = "ID do usuário não encontrado. Tente novamente."
                    )
                    return
                }
                
                val token = AuthSession.token
                
                if (token.isNullOrBlank()) {
                    _state.value = currentState.copy(
                        errorMessage = "Token de autenticação não encontrado"
                    )
                    return
                }
                
                if (currentState.selectedImages.isEmpty()) {
                    _state.value = currentState.copy(
                        errorMessage = "Selecione pelo menos uma imagem"
                    )
                    return
                }
                
                _state.value = currentState.copy(isLoading = true, errorMessage = null)
                
                viewModelScope.launch {
                    try {
                        val results = mutableListOf<Result<String>>()
                        
                        for (uri in currentState.selectedImages) {
                            val bytes = getBytesFromUri(uri)
                            if (bytes != null) {
                                val mimeType = context?.contentResolver?.getType(uri) ?: "image/jpeg"
                                val fileName = "image_${System.currentTimeMillis()}.jpg"
                                val result = anuncioRepository.uploadNovaFoto(userId, token, bytes, fileName, mimeType)
                                results.add(result)
                            } else {
                                results.add(Result.failure(Exception("Erro ao ler imagem")))
                            }
                        }
                        
                        val hasErrors = results.any { it.isFailure }
                        
                        if (hasErrors) {
                            _state.value = currentState.copy(
                                isLoading = false,
                                errorMessage = "Erro ao fazer upload de algumas imagens"
                            )
                        } else {
                            _state.value = currentState.copy(
                                isLoading = false,
                                isCompleted = true
                            )
                        }
                    } catch (e: Exception) {
                        _state.value = currentState.copy(
                            isLoading = false,
                            errorMessage = "Erro ao fazer upload das imagens: ${e.message}"
                        )
                    }
                }
            }
            is UploadImagesEvent.ClearError -> {
                _state.value = currentState.copy(errorMessage = null)
            }
            is UploadImagesEvent.Skip -> {
                _state.value = currentState.copy(isSkipped = true)
            }
        }
    }
    
    fun setAnuncioId(anuncioId: Long) {
        _state.value = _state.value.copy(anuncioId = anuncioId)
        fetchUserId(anuncioId)
    }
    
    private fun fetchUserId(anuncioId: Long) {
        viewModelScope.launch {
            val token = AuthSession.token ?: return@launch
            val result = anuncioRepository.visualizarAnuncio(anuncioId, token)
            result.fold(
                onSuccess = { anuncio ->
                    _state.value = _state.value.copy(userId = anuncio.idUsuarioOfertante)
                },
                onFailure = { exception ->
                    _state.value = _state.value.copy(
                        errorMessage = "Erro ao obter informações do anúncio: ${exception.message}"
                    )
                }
            )
        }
    }
    
    private fun getBytesFromUri(uri: Uri): ByteArray? {
        return try {
            context?.contentResolver?.openInputStream(uri)?.use { inputStream ->
                inputStream.readBytes()
            }
        } catch (e: Exception) {
            null
        }
    }
}