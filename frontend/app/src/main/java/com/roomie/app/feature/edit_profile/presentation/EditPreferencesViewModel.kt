package com.roomie.app.feature.edit_profile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.roomie.app.core.data.api.ProfileApiService
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.preference_registration.model.UserPreferences
import com.roomie.app.feature.profile.data.ProfileRepository
import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioOfertanteDto
import com.roomie.app.feature.profile.model.toUserPreferences
import kotlinx.coroutines.launch

data class EditPreferencesUiState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val preferences: UserPreferences? = null,
    val hasExistingPreferences: Boolean = false,
    val errorMessage: String? = null,
    val saveSuccessful: Boolean = false,
)

class EditPreferencesViewModel(
    private val repository: ProfileRepository,
    private val api: ProfileApiService,
) : ViewModel() {

    private var interessesId: Long? = null

    var uiState by mutableStateOf(EditPreferencesUiState())
        private set

    fun loadPreferences(role: ProfileRole, userId: Long, token: String, forceReload: Boolean = false) {
        if (!forceReload && (uiState.isLoading || uiState.preferences != null)) return

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null, preferences = null)

            val profileResult = repository.getUserProfile(role, userId, token)

            profileResult.onSuccess { profile ->
                val hasExisting: Boolean
                val drinksAlcohol: Boolean

                when (role) {
                    ProfileRole.SEEKER -> {
                        val dtoResult = getUsuarioInteressadoDto(userId, token)
                        val dto = dtoResult.getOrNull()
                        hasExisting = dto?.interesses?.id != null
                        drinksAlcohol = dto?.interesses?.consome_bebidas_alcoolicas == true
                        interessesId = dto?.interesses?.id
                    }
                    ProfileRole.OFFEROR -> {
                        val dtoResult = getUsuarioOfertanteDto(userId, token)
                        val dto = dtoResult.getOrNull()
                        hasExisting = dto?.interesses?.id != null
                        drinksAlcohol = false
                        interessesId = dto?.interesses?.id 
                    }
                }

                val userPreferences = profile.toUserPreferences(drinksAlcohol = drinksAlcohol)

                uiState = uiState.copy(
                    isLoading = false,
                    preferences = userPreferences,
                    hasExistingPreferences = hasExisting
                )
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = throwable.message ?: "Erro ao carregar preferências"
                )
            }
        }
    }

    private suspend fun getUsuarioInteressadoDto(
        userId: Long,
        token: String
    ): Result<UsuarioInteressadoDto> {
        return try {
            val auth = "Bearer $token"
            val response = api.getUsuarioInteressado(userId, auth)
            if (!response.isSuccessful) {
                Result.failure(IllegalStateException("Erro da API: ${response.code()} ${response.message()}"))
            } else {
                val body = response.body()
                if (body == null) {
                    Result.failure(IllegalStateException("Resposta da API vazia"))
                } else {
                    Result.success(body)
                }
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    private suspend fun getUsuarioOfertanteDto(
        userId: Long,
        token: String
    ): Result<UsuarioOfertanteDto> {
        return try {
            val auth = "Bearer $token"
            val response = api.getUsuarioOfertante(userId, auth)
            if (!response.isSuccessful) {
                Result.failure(IllegalStateException("Erro da API: ${response.code()} ${response.message()}"))
            } else {
                val body = response.body()
                if (body == null) {
                    Result.failure(IllegalStateException("Resposta da API vazia"))
                } else {
                    Result.success(body)
                }
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    fun savePreferences(role: ProfileRole, token: String, preferences: UserPreferences) {
        val id = interessesId
        if (id == null) {
            uiState = uiState.copy(
                isSaving = false,
                errorMessage = "ID dos interesses não encontrado"
            )
            return
        }
        viewModelScope.launch {
            uiState = uiState.copy(isSaving = true, errorMessage = null, saveSuccessful = false)

            val hasExisting = uiState.hasExistingPreferences

            val result = repository.upsertPreferences(role, id, token, hasExisting, preferences)

            result.onSuccess {
                loadPreferences(role, id, token, forceReload = true)
                uiState = uiState.copy(isSaving = false, saveSuccessful = true)
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    isSaving = false,
                    errorMessage = throwable.message ?: "Erro ao salvar preferências"
                )
            }
        }
    }

    fun consumeSaveSuccess() {
        uiState = uiState.copy(saveSuccessful = false)
    }
}

class EditPreferencesViewModelFactory(
    private val repository: ProfileRepository,
    private val api: ProfileApiService,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditPreferencesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditPreferencesViewModel(repository, api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
