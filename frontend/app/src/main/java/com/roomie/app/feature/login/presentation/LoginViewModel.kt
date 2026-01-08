package com.roomie.app.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.core.model.profileRoleFromApi
import com.roomie.app.feature.login.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccessful: Boolean = false
)

class LoginViewModel(
    private val authRepository: AuthRepository
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

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoginSuccessful = true,
                        errorMessage = null
                    )
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
}
