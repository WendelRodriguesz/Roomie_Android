package com.roomie.app.feature.offeror_home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.roomie.app.feature.offeror_home.data.AnuncioRepository
import com.roomie.app.feature.offeror_home.data.remote.dto.AtualizarAnuncioRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditAnuncioViewModel(
    private val repository: AnuncioRepository,
    private val anuncioId: Long,
    private val userId: Long,
    private val token: String
) : ViewModel() {

    private val _state = MutableStateFlow(EditAnuncioState())
    val state: StateFlow<EditAnuncioState> = _state.asStateFlow()

    private val _titulo = MutableStateFlow("")
    val titulo: StateFlow<String> = _titulo.asStateFlow()

    private val _descricao = MutableStateFlow("")
    val descricao: StateFlow<String> = _descricao.asStateFlow()

    private val _rua = MutableStateFlow("")
    val rua: StateFlow<String> = _rua.asStateFlow()

    private val _numero = MutableStateFlow("")
    val numero: StateFlow<String> = _numero.asStateFlow()

    private val _bairro = MutableStateFlow("")
    val bairro: StateFlow<String> = _bairro.asStateFlow()

    private val _cidade = MutableStateFlow("")
    val cidade: StateFlow<String> = _cidade.asStateFlow()

    private val _estado = MutableStateFlow("")
    val estado: StateFlow<String> = _estado.asStateFlow()

    private val _valorAluguel = MutableStateFlow("")
    val valorAluguel: StateFlow<String> = _valorAluguel.asStateFlow()

    private val _valorContas = MutableStateFlow("")
    val valorContas: StateFlow<String> = _valorContas.asStateFlow()

    private val _vagasDisponiveis = MutableStateFlow("")
    val vagasDisponiveis: StateFlow<String> = _vagasDisponiveis.asStateFlow()

    private val _tipoImovel = MutableStateFlow("")
    val tipoImovel: StateFlow<String> = _tipoImovel.asStateFlow()

    private val _comodos = MutableStateFlow<List<String>>(emptyList())
    val comodos: StateFlow<List<String>> = _comodos.asStateFlow()

    init {
        loadAnuncio()
    }

    fun handleEvent(event: EditAnuncioEvent) {
        when (event) {
            is EditAnuncioEvent.UpdateTitulo -> _titulo.value = event.titulo
            is EditAnuncioEvent.UpdateDescricao -> _descricao.value = event.descricao
            is EditAnuncioEvent.UpdateRua -> _rua.value = event.rua
            is EditAnuncioEvent.UpdateNumero -> _numero.value = event.numero
            is EditAnuncioEvent.UpdateBairro -> _bairro.value = event.bairro
            is EditAnuncioEvent.UpdateCidade -> _cidade.value = event.cidade
            is EditAnuncioEvent.UpdateEstado -> _estado.value = event.estado
            is EditAnuncioEvent.UpdateValorAluguel -> _valorAluguel.value = event.valor
            is EditAnuncioEvent.UpdateValorContas -> _valorContas.value = event.valor
            is EditAnuncioEvent.UpdateVagasDisponiveis -> _vagasDisponiveis.value = event.vagas
            is EditAnuncioEvent.UpdateTipoImovel -> _tipoImovel.value = event.tipo
            is EditAnuncioEvent.UpdateComodos -> _comodos.value = event.comodos
            is EditAnuncioEvent.LoadAnuncio -> loadAnuncio()
            is EditAnuncioEvent.SaveAnuncio -> saveAnuncio()
            is EditAnuncioEvent.UploadFoto -> uploadFoto(event.bytes, event.fileName, event.mimeType)
            is EditAnuncioEvent.RemoveFoto -> removeFoto(event.fotoUrl)
            is EditAnuncioEvent.DismissError -> _state.update { it.copy(errorMessage = null) }
            is EditAnuncioEvent.DismissSuccess -> _state.update { it.copy(successMessage = null) }
        }
    }

    private fun loadAnuncio() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val result = repository.visualizarAnuncio(anuncioId, token)

            result.fold(
                onSuccess = { anuncio ->
                    _titulo.value = anuncio.titulo
                    _descricao.value = anuncio.descricao
                    _rua.value = anuncio.rua
                    _numero.value = anuncio.numero
                    _bairro.value = anuncio.bairro
                    _cidade.value = anuncio.cidade
                    _estado.value = anuncio.estado
                    _valorAluguel.value = anuncio.valorAluguel.toString()
                    _valorContas.value = anuncio.valorContas.toString()
                    _vagasDisponiveis.value = anuncio.vagasDisponiveis.toString()
                    _tipoImovel.value = anuncio.tipoImovel
                    _comodos.value = anuncio.comodos

                    _state.update {
                        it.copy(
                            isLoading = false,
                            anuncio = anuncio
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Erro ao carregar anúncio"
                        )
                    }
                }
            )
        }
    }

    private fun saveAnuncio() {
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, errorMessage = null, successMessage = null) }

            val valorAluguelFloat = try {
                _valorAluguel.value.toFloatOrNull() ?: 0f
            } catch (e: Exception) {
                0f
            }

            val valorContasFloat = try {
                _valorContas.value.toFloatOrNull() ?: 0f
            } catch (e: Exception) {
                0f
            }

            val vagasInt = try {
                _vagasDisponiveis.value.toIntOrNull() ?: 0
            } catch (e: Exception) {
                0
            }

            val request = AtualizarAnuncioRequest(
                titulo = _titulo.value,
                descricao = _descricao.value,
                rua = _rua.value,
                numero = _numero.value,
                bairro = _bairro.value,
                cidade = _cidade.value,
                estado = _estado.value,
                valorAluguel = valorAluguelFloat,
                valorContas = valorContasFloat,
                vagasDisponiveis = vagasInt,
                tipo_imovel = _tipoImovel.value,
                comodos = _comodos.value
            )

            val result = repository.atualizarAnuncio(anuncioId, token, request)

            result.fold(
                onSuccess = { anuncio ->
                    _state.update {
                        it.copy(
                            isSaving = false,
                            anuncio = anuncio,
                            successMessage = "Anúncio atualizado com sucesso!"
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = throwable.message ?: "Erro ao atualizar anúncio"
                        )
                    }
                }
            )
        }
    }

    private fun uploadFoto(bytes: ByteArray, fileName: String, mimeType: String?) {
        viewModelScope.launch {
            _state.update { it.copy(isUploadingPhoto = true, errorMessage = null) }
            
            val result = repository.uploadNovaFoto(userId, token, bytes, fileName, mimeType)
            
            result.fold(
                onSuccess = { fotoUrl ->
                    // Recarrega o anúncio para atualizar a lista de fotos
                    loadAnuncio()
                    _state.update { 
                        it.copy(
                            isUploadingPhoto = false,
                            successMessage = "Foto adicionada com sucesso!"
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isUploadingPhoto = false,
                            errorMessage = throwable.message ?: "Erro ao fazer upload da foto"
                        )
                    }
                }
            )
        }
    }

    private fun removeFoto(fotoUrl: String) {
        viewModelScope.launch {
            _state.update { it.copy(isDeletingPhoto = true, errorMessage = null) }
            
            val result = repository.deletarFoto(userId, token, fotoUrl)
            
            result.fold(
                onSuccess = {
                    loadAnuncio()
                    _state.update { 
                        it.copy(
                            isDeletingPhoto = false,
                            successMessage = "Foto removida com sucesso!"
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isDeletingPhoto = false,
                            errorMessage = throwable.message ?: "Erro ao remover foto"
                        )
                    }
                }
            )
        }
    }
}

class EditAnuncioViewModelFactory(
    private val repository: AnuncioRepository,
    private val anuncioId: Long,
    private val userId: Long,
    private val token: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditAnuncioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditAnuncioViewModel(repository, anuncioId, userId, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
