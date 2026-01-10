package com.roomie.app.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.core.model.profileRoleFromApi
import com.roomie.app.feature.login.data.AuthRepository
import com.roomie.app.feature.profile.data.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccessful: Boolean = false,
    val needsPreferencesSetup: Boolean = false
)

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository = ProfileRepository(RetrofitClient.profileApiService)
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, senha: String) {
        if (email.isBlank() || senha.isBlank()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Por favor, preencha todos os campos",
                isLoginSuccessful = false
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                isLoginSuccessful = false
            )

            authRepository.login(email, senha)
                .onSuccess { loginResponse ->
                    val role = profileRoleFromApi(loginResponse.role)

                    if (role == null) {
                        AuthSession.clear()
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isLoginSuccessful = false,
                            errorMessage = "Role inválida retornada pela API: ${loginResponse.role}"
                        )
                        return@onSuccess
                    }

                    AuthSession.userId = loginResponse.id
                    AuthSession.token = loginResponse.token
                    AuthSession.refreshToken = loginResponse.refreshToken
                    AuthSession.role = role

                    checkUserInterests(loginResponse.id, loginResponse.token, role)
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoginSuccessful = false,
                        errorMessage = exception.message ?: "Erro ao fazer login. Tente novamente."
                    )
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    private fun checkUserInterests(userId: Long, token: String, role: ProfileRole) {
        viewModelScope.launch {
            try {
                val auth = "Bearer $token"
                val api = RetrofitClient.profileApiService

                val needsPreferences = when (role) {
                    ProfileRole.SEEKER -> {
                        val response = api.getUsuarioInteressado(userId, auth)
                        if (response.isSuccessful && response.body() != null) {
                            val dto = response.body()!!
                            dto.interesses == null
                        } else {
                            false
                        }
                    }
                    ProfileRole.OFFEROR -> {
                        val response = api.getUsuarioOfertante(userId, auth)
                        if (response.isSuccessful && response.body() != null) {
                            val dto = response.body()!!
                            dto.interesses == null
                        } else {
                            false
                        }
                    }
                }
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoginSuccessful = true,
                    errorMessage = null,
                    needsPreferencesSetup = needsPreferences
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoginSuccessful = true,
                    errorMessage = null,
                    needsPreferencesSetup = false
                )
            }
        }
    }
}
