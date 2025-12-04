package com.roomie.app.feature.login.data

import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.data.local.AuthDataStore
import com.roomie.app.core.data.model.UserSession
import com.roomie.app.feature.login.data.model.LoginRequest
import com.roomie.app.feature.login.data.model.LoginResponse

class AuthRepository(
    private val authDataStore: AuthDataStore
) {
    private val authApiService = RetrofitClient.authApiService

    suspend fun login(email: String, senha: String): Result<LoginResponse> {
        return try {
            val request = LoginRequest(email, senha)
            val response = authApiService.login(request)
            
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                
                // Salvar sessão no DataStore
                val session = UserSession(
                    id = loginResponse.id,
                    token = loginResponse.token,
                    refreshToken = loginResponse.refreshToken,
                    role = loginResponse.role
                )
                authDataStore.saveUserSession(session)
                
                Result.success(loginResponse)
            } else {
                val errorMessage = response.errorBody()?.string() 
                    ?: "Erro ao fazer login. Tente novamente."
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        authDataStore.clearUserSession()
    }

    fun getUserSession() = authDataStore.userSession
}

