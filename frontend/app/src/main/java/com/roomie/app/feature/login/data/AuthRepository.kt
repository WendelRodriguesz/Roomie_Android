package com.roomie.app.feature.login.data

import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.data.local.AuthDataStore
import com.roomie.app.core.data.model.UserSession
import com.roomie.app.feature.login.data.model.LoginRequest
import com.roomie.app.feature.login.data.model.LoginResponse
import com.roomie.app.feature.login.data.model.RefreshRequest
import kotlinx.coroutines.flow.firstOrNull

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

    suspend fun refreshSession(): Result<UserSession> {
        return try {
            val currentSession = authDataStore.userSession.firstOrNull()

            val refreshToken = currentSession?.refreshToken
            if (refreshToken.isNullOrBlank()) {
                Result.failure(Exception("Nenhum refresh token encontrado."))
            } else {
                val request = RefreshRequest(refreshToken)
                val response = authApiService.refresh(request)

                if (response.isSuccessful && response.body() != null) {
                    val refreshResponse = response.body()!!

                    val updatedSession = currentSession.copy(
                        token = refreshResponse.token,
                        refreshToken = refreshResponse.refreshToken,
                        role = refreshResponse.role
                    )

                    authDataStore.saveUserSession(updatedSession)

                    Result.success(updatedSession)
                } else {
                    authDataStore.clearUserSession()

                    val errorMessage = response.errorBody()?.string()
                        ?: "Erro ao atualizar sessão. Faça login novamente."
                    Result.failure(Exception(errorMessage))
                }
            }
        } catch (e: Exception) {
            authDataStore.clearUserSession()
            Result.failure(e)
        }
    }

    suspend fun logout() {
        authDataStore.clearUserSession()
    }

    fun getUserSession() = authDataStore.userSession
}

