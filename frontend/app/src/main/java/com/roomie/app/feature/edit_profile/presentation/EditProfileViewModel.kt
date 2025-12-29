package com.roomie.app.feature.edit_profile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.roomie.app.feature.profile.data.ProfileRepository
import com.roomie.app.feature.profile.model.UserProfile
import kotlinx.coroutines.launch

data class EditProfileUiState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val profile: UserProfile? = null,
    val errorMessage: String? = null,
    val saveSuccessful: Boolean = false,
)

class EditProfileViewModel(
    private val repository: ProfileRepository,
) : ViewModel() {

    var uiState by mutableStateOf(EditProfileUiState())
        private set

    fun loadProfile(userId: Long, token: String) {
        if (uiState.isLoading || uiState.profile != null) return

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            val result = repository.getUserProfile(userId, token)

            result.onSuccess { profile ->
                uiState = uiState.copy(
                    isLoading = false,
                    profile = profile
                )
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = throwable.message ?: "Erro ao carregar perfil"
                )
            }
        }
    }

    fun saveProfile(userId: Long, token: String, updatedProfile: UserProfile) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isSaving = true,
                errorMessage = null,
                saveSuccessful = false,
            )

            val result = repository.updateUserProfile(userId, token, updatedProfile)

            result.onSuccess { profile ->
                uiState = uiState.copy(
                    isSaving = false,
                    profile = profile,
                    saveSuccessful = true,
                )
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    isSaving = false,
                    errorMessage = throwable.message ?: "Erro ao salvar perfil",
                    saveSuccessful = false,
                )
            }
        }
    }

    fun consumeSaveSuccess() {
        uiState = uiState.copy(saveSuccessful = false)
    }
}

class EditProfileViewModelFactory(
    private val repository: ProfileRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
