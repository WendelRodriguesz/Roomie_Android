package com.roomie.app.feature.register.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.profile.model.GenderOption
import com.roomie.app.feature.register.data.RegisterRepository
import com.roomie.app.feature.register.data.remote.dto.RegisterRequest
import com.roomie.app.feature.register.model.RegisterFormState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegistrationSuccessful: Boolean = false
)

class RegisterViewModel(
    private val repository: RegisterRepository
) : ViewModel() {

    private var formState = RegisterFormState()

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun setDadosBasicos(
        nome: String,
        email: String,
        senha: String,
        dataNascimento: String,
        cidade: String,
        genero: GenderOption?
    ) {
        formState = formState.copy(
            nome = nome,
            email = email,
            senha = senha,
            dataNascimento = dataNascimento,
            cidade = cidade,
            genero = genero?.apiValue ?: ""
        )
    }

    fun completeRegistration(role: ProfileRole) {
        if (
            formState.nome.isBlank() ||
            formState.email.isBlank() ||
            formState.senha.isBlank() ||
            formState.dataNascimento.isBlank() ||
            formState.cidade.isBlank() ||
            formState.genero.isBlank()
        ) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Todos os campos são obrigatórios"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val request = RegisterRequest(
                nome = formState.nome,
                email = formState.email,
                senha = formState.senha,
                data_de_nascimento = formState.dataNascimento,
                cidade = formState.cidade,
                genero = formState.genero
            )

            repository.createUser(role, request)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRegistrationSuccessful = true,
                        errorMessage = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRegistrationSuccessful = false,
                        errorMessage = exception.message ?: "Erro ao cadastrar usuário"
                    )
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetRegistrationState() {
        _uiState.value = RegisterUiState()
    }
}

class RegisterViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            val repository = RegisterRepository()
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
