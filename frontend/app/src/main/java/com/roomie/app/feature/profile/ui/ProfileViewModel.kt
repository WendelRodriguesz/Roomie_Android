package com.roomie.app.feature.profile.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.roomie.app.feature.profile.data.ProfileRepository
import com.roomie.app.feature.profile.model.UserProfile
import kotlinx.coroutines.launch
import com.roomie.app.core.model.ProfileRole

data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: UserProfile? = null,
    val errorMessage: String? = null,
)


class ProfileViewModel(
    private val repository: ProfileRepository,
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
                uiState = uiState.copy(isLoading = false, profile = profile)
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = throwable.message ?: "Erro ao carregar perfil",
                )
            }
        }
    }

}

class ProfileViewModelFactory(
    private val repository: ProfileRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
