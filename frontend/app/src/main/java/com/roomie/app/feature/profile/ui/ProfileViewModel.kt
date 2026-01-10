package com.roomie.app.feature.profile.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.roomie.app.core.data.api.ProfileApiService
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.profile.data.ProfileRepository
import com.roomie.app.feature.profile.model.UserProfile
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: UserProfile? = null,
    val errorMessage: String? = null,
    val hasInterests: Boolean = true, // Por padrão assume que tem, será atualizado
)


class ProfileViewModel(
    private val repository: ProfileRepository,
    private val api: ProfileApiService,
) : ViewModel() {

    var uiState by mutableStateOf(ProfileUiState())
        private set

    fun loadProfile(role: ProfileRole, userId: Long, token: String, forceReload: Boolean = false) {
        if (!forceReload) {
            if (uiState.isLoading || uiState.profile != null) return
        } else {
            if (uiState.isLoading) return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            val result = repository.getUserProfile(role, userId, token)

            result.onSuccess { profile ->
                // Verificar se os interesses existem
                val hasInterests = checkIfInterestsExist(role, userId, token)
                uiState = uiState.copy(isLoading = false, profile = profile, hasInterests = hasInterests)
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = throwable.message ?: "Erro ao carregar perfil",
                )
            }
        }
    }

    private suspend fun checkIfInterestsExist(role: ProfileRole, userId: Long, token: String): Boolean {
        return try {
            val auth = "Bearer $token"
            when (role) {
                ProfileRole.SEEKER -> {
                    val response = api.getUsuarioInteressado(userId, auth)
                    if (response.isSuccessful && response.body() != null) {
                        response.body()!!.interesses != null
                    } else {
                        false
                    }
                }
                ProfileRole.OFFEROR -> {
                    val response = api.getUsuarioOfertante(userId, auth)
                    if (response.isSuccessful && response.body() != null) {
                        response.body()!!.interesses != null
                    } else {
                        false
                    }
                }
            }
        } catch (e: Exception) {
            false
        }
    }

}

class ProfileViewModelFactory(
    private val repository: ProfileRepository,
    private val api: ProfileApiService,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository, api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
