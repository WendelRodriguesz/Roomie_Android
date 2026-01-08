package com.roomie.app.core.data.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.roomie.app.core.data.api.FirebaseTokenApiService
import com.roomie.app.core.data.api.FirebaseTokenRequest
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.data.local.AuthDataStore
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.core.model.profileRoleFromApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FirebaseTokenManager(
    private val context: Context,
    private val authDataStore: AuthDataStore,
    private val apiService: FirebaseTokenApiService = RetrofitClient.firebaseTokenApiService
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    companion object {
        private const val TAG = "FirebaseTokenManager"
    }

    /**
     * Obtém o token do Firebase e envia para o backend
     * Deve ser chamado após o login ou quando a sessão é restaurada
     */
    fun sendTokenToBackend() {
        scope.launch {
            try {
                // Obter sessão atual do usuário
                val session = authDataStore.userSession.firstOrNull()
                
                if (session == null) {
                    Log.d(TAG, "Nenhuma sessão encontrada, não é possível enviar token")
                    return@launch
                }

                val role = profileRoleFromApi(session.role)
                if (role == null) {
                    Log.e(TAG, "Role inválida: ${session.role}")
                    return@launch
                }

                // Obter token do Firebase
                val firebaseToken = try {
                    FirebaseMessaging.getInstance().token.await()
                } catch (e: Exception) {
                    Log.e(TAG, "Erro ao obter token do Firebase", e)
                    null
                }

                if (firebaseToken == null) {
                    Log.e(TAG, "Token do Firebase é nulo")
                    return@launch
                }

                Log.d(TAG, "Token FCM obtido: $firebaseToken")

                // Preparar requisição
                val request = FirebaseTokenRequest(firebase_token = firebaseToken)
                val authHeader = "Bearer ${session.token}"

                // Enviar para o endpoint correto baseado no role
                val response = if (role == ProfileRole.SEEKER) {
                    apiService.cadastrarTokenInteressado(
                        idUsuario = session.id,
                        token = authHeader,
                        request = request
                    )
                } else {
                    apiService.cadastrarTokenOfertante(
                        idUsuario = session.id,
                        token = authHeader,
                        request = request
                    )
                }

                if (response.isSuccessful) {
                    Log.d(TAG, "Token enviado com sucesso para o backend")
                } else {
                    Log.e(
                        TAG,
                        "Erro ao enviar token para o backend: ${response.code()} - ${response.message()}"
                    )
                    response.errorBody()?.string()?.let {
                        Log.e(TAG, "Corpo do erro: $it")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao enviar token para o backend", e)
            }
        }
    }

    /**
     * Versão síncrona para uso em coroutines
     */
    suspend fun sendTokenToBackendSuspend() {
        try {
            // Obter sessão atual do usuário
            val session = authDataStore.userSession.firstOrNull()
            
            if (session == null) {
                Log.d(TAG, "Nenhuma sessão encontrada, não é possível enviar token")
                return
            }

            val role = profileRoleFromApi(session.role)
            if (role == null) {
                Log.e(TAG, "Role inválida: ${session.role}")
                return
            }

            // Obter token do Firebase
            val firebaseToken = try {
                FirebaseMessaging.getInstance().token.await()
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao obter token do Firebase", e)
                null
            }

            if (firebaseToken == null) {
                Log.e(TAG, "Token do Firebase é nulo")
                return
            }

            Log.d(TAG, "Token FCM obtido: $firebaseToken")

            // Preparar requisição
            val request = FirebaseTokenRequest(firebase_token = firebaseToken)
            val authHeader = "Bearer ${session.token}"

            // Enviar para o endpoint correto baseado no role
            val response = if (role == ProfileRole.SEEKER) {
                apiService.cadastrarTokenInteressado(
                    idUsuario = session.id,
                    token = authHeader,
                    request = request
                )
            } else {
                apiService.cadastrarTokenOfertante(
                    idUsuario = session.id,
                    token = authHeader,
                    request = request
                )
            }

            if (response.isSuccessful) {
                Log.d(TAG, "Token enviado com sucesso para o backend")
            } else {
                Log.e(
                    TAG,
                    "Erro ao enviar token para o backend: ${response.code()} - ${response.message()}"
                )
                response.errorBody()?.string()?.let {
                    Log.e(TAG, "Corpo do erro: $it")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao enviar token para o backend", e)
        }
    }
}

