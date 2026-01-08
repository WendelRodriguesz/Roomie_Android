package com.roomie.app.core.data.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.roomie.app.core.data.local.AuthDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FCMService : FirebaseMessagingService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        // Criar canal de notificações quando o serviço é criado
        NotificationHelper.createNotificationChannel(applicationContext)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Novo token FCM recebido: $token")
        
        // Quando um novo token é gerado, tentar enviar para o backend
        // se o usuário estiver logado
        serviceScope.launch {
            try {
                val authDataStore = AuthDataStore(applicationContext)
                val tokenManager = FirebaseTokenManager(applicationContext, authDataStore)
                tokenManager.sendTokenToBackendSuspend()
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao enviar novo token para o backend", e)
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "Mensagem recebida: ${remoteMessage.messageId}")
        
        // Extrair a mensagem do payload
        // O formato esperado é uma String "mensagem" no payload
        val mensagem = remoteMessage.data["mensagem"] 
            ?: remoteMessage.notification?.body 
            ?: "Nova notificação"
        
        Log.d(TAG, "Mensagem extraída: $mensagem")
        
        // Exibir notificação na barra de notificações
        val notificationHelper = NotificationHelper(applicationContext)
        notificationHelper.showNotification(mensagem)
    }

    companion object {
        private const val TAG = "FCMService"
        
        /**
         * Obtém o token atual do Firebase Cloud Messaging de forma assíncrona
         */
        suspend fun getCurrentToken(): String? {
            return try {
                FirebaseMessaging.getInstance().token.await()
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao obter token FCM", e)
                null
            }
        }

        /**
         * Força a atualização do token FCM
         */
        fun refreshToken(onComplete: (String?) -> Unit) {
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val token = task.result
                        Log.d(TAG, "Token atualizado: $token")
                        onComplete(token)
                    } else {
                        Log.e(TAG, "Erro ao atualizar token", task.exception)
                        onComplete(null)
                    }
                }
        }
    }
}

