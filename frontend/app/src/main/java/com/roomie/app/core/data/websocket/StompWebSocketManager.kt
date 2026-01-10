package com.roomie.app.core.data.websocket

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.feature.chat.data.remote.dto.MensagemCreateDto
import com.roomie.app.feature.chat.data.remote.dto.MensagemDto
import com.roomie.app.feature.chat.model.Mensagem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage

class StompWebSocketManager private constructor() {
    companion object {
        @Volatile
        private var INSTANCE: StompWebSocketManager? = null

        fun getInstance(): StompWebSocketManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: StompWebSocketManager().also { INSTANCE = it }
            }
        }
    }

    private val gson = Gson()
    private var stompClient: StompClient? = null
    private var isConnected = false
    private var isConnecting = false
    private val disposables = CompositeDisposable()
    private val activeSubscriptions = mutableMapOf<Long, io.reactivex.disposables.Disposable>()
    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val WS_BASE_URL = "https://roomie-wi19.onrender.com"
    private val WS_ENDPOINT = "/ws-chat/websocket"

    fun ensureConnection() {
        // Se já está conectado, não fazer nada
        if (stompClient != null && isConnected) {
            return
        }

        // Se já está tentando conectar, não fazer nada
        if (isConnecting) {
            return
        }

        // Se tem cliente mas não está conectado, limpar primeiro
        if (stompClient != null && !isConnected) {
            try {
                disposables.clear()
                stompClient?.disconnect()
            } catch (e: Exception) {
                Log.w("StompWebSocket", "Error disconnecting old client: ${e.message}")
            }
            stompClient = null
        }

        isConnecting = true
        _connectionState.value = ConnectionState.Connecting

        try {
            val wsUrl = "$WS_BASE_URL$WS_ENDPOINT"
            stompClient = Stomp.over(
                Stomp.ConnectionProvider.OKHTTP,
                wsUrl
            )
            Log.d("StompWebSocket", "Connecting to: $wsUrl")

            val lifecycleSub = stompClient?.lifecycle()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { lifecycleEvent ->
                    when (lifecycleEvent.type) {
                        LifecycleEvent.Type.OPENED -> {
                            isConnected = true
                            isConnecting = false
                            _connectionState.value = ConnectionState.Connected
                            Log.d("StompWebSocket", "Connected to WebSocket")
                        }
                        LifecycleEvent.Type.CLOSED -> {
                            isConnected = false
                            isConnecting = false
                            _connectionState.value = ConnectionState.Disconnected
                            Log.d("StompWebSocket", "Disconnected from WebSocket")
                        }
                        LifecycleEvent.Type.ERROR -> {
                            isConnected = false
                            isConnecting = false
                            val errorMsg = lifecycleEvent.exception?.message ?: "Unknown error"
                            _connectionState.value = ConnectionState.Error(errorMsg)
                            Log.e("StompWebSocket", "WebSocket error: ${lifecycleEvent.exception}")
                        }
                        else -> {}
                    }
                }

            lifecycleSub?.let { disposables.add(it) }

            stompClient?.connect()
        } catch (e: Exception) {
            isConnecting = false
            val errorMsg = e.message ?: "Connection error"
            _connectionState.value = ConnectionState.Error(errorMsg)
            Log.e("StompWebSocket", "Error connecting: ${e.message}", e)
        }
    }

    fun subscribeToChat(chatId: Long, onMessageReceived: (Mensagem) -> Unit): io.reactivex.disposables.Disposable? {
        if (!isConnected || stompClient == null) {
            Log.e("StompWebSocket", "Cannot subscribe: not connected")
            return null
        }
        
        // Se já existe uma subscrição ativa para este chat, desinscrever primeiro
        activeSubscriptions[chatId]?.let { oldSub ->
            try {
                if (!oldSub.isDisposed) {
                    oldSub.dispose()
                }
            } catch (e: Exception) {
                Log.w("StompWebSocket", "Error disposing old subscription: ${e.message}")
            }
            activeSubscriptions.remove(chatId)
        }
        
        val destination = "/topic/chat/$chatId"
        
        return try {
            val topicSub = stompClient?.topic(destination)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { stompMessage: StompMessage ->
                    try {
                        val payload = stompMessage.payload
                        Log.d("StompWebSocket", "Received message: $payload")
                        val mensagemDto = gson.fromJson(payload, MensagemDto::class.java)
                        
                        val userId = AuthSession.userId
                        val mensagem = Mensagem(
                            id = mensagemDto.id,
                            idChat = mensagemDto.id_chat,
                            idRemetente = mensagemDto.id_remetente,
                            idDestinatario = mensagemDto.id_destinatario,
                            conteudo = mensagemDto.conteudo,
                            enviadaEm = mensagemDto.enviada_em,
                            isMine = mensagemDto.id_remetente == userId
                        )
                        
                        Log.d("StompWebSocket", "Parsed message: id=${mensagem.id}, isMine=${mensagem.isMine}")
                        onMessageReceived(mensagem)
                    } catch (e: JsonSyntaxException) {
                        Log.e("StompWebSocket", "Error parsing message: ${e.message}", e)
                    } catch (e: Exception) {
                        Log.e("StompWebSocket", "Error processing message: ${e.message}", e)
                    }
                }

            topicSub?.let { 
                activeSubscriptions[chatId] = it
                disposables.add(it)
            }
            Log.d("StompWebSocket", "Subscribed to $destination")
            topicSub
        } catch (e: Exception) {
            Log.e("StompWebSocket", "Error subscribing to $destination: ${e.message}", e)
            null
        }
    }
    
    fun unsubscribeFromChat(chatId: Long) {
        activeSubscriptions[chatId]?.let { sub ->
            try {
                if (!sub.isDisposed) {
                    sub.dispose()
                }
            } catch (e: Exception) {
                Log.w("StompWebSocket", "Error unsubscribing from chat $chatId: ${e.message}")
            }
            activeSubscriptions.remove(chatId)
            Log.d("StompWebSocket", "Unsubscribed from chat $chatId")
        }
    }

    fun sendMessage(chatId: Long, destinatarioId: Long, conteudo: String): Boolean {
        if (!isConnected || stompClient == null) {
            Log.e("StompWebSocket", "Cannot send message: not connected")
            return false
        }

        val remetenteId = AuthSession.userId ?: return false

        try {
            val mensagemCreateDto = MensagemCreateDto(
                id_chat = chatId.toInt(),
                id_remetente = remetenteId.toInt(),
                id_destinatario = destinatarioId.toInt(),
                conteudo = conteudo
            )

            val jsonMessage = gson.toJson(mensagemCreateDto)
            val destination = "/app/chat/enviar"

            stompClient?.send(destination, jsonMessage)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                    { Log.d("StompWebSocket", "Message sent to $destination: $jsonMessage") },
                    { error -> Log.e("StompWebSocket", "Error sending message: ${error.message}", error) }
                )?.let { disposables.add(it) }

            return true
        } catch (e: Exception) {
            Log.e("StompWebSocket", "Error sending message: ${e.message}", e)
            return false
        }
    }

    fun disconnect() {
        try {
            // Limpar todas as subscrições
            activeSubscriptions.values.forEach { sub ->
                try {
                    if (!sub.isDisposed) {
                        sub.dispose()
                    }
                } catch (e: Exception) {
                    Log.w("StompWebSocket", "Error disposing subscription: ${e.message}")
                }
            }
            activeSubscriptions.clear()
            
            disposables.clear()
            isConnected = false
            
            try {
                stompClient?.disconnect()
            } catch (e: Exception) {
                Log.w("StompWebSocket", "Error during disconnect: ${e.message}")
            }
            
            stompClient = null
            _connectionState.value = ConnectionState.Disconnected
            Log.d("StompWebSocket", "Disconnected from WebSocket")
        } catch (e: Exception) {
            Log.e("StompWebSocket", "Error disconnecting: ${e.message}", e)
        }
    }
    
    fun isConnected(): Boolean = isConnected && stompClient != null

    sealed class ConnectionState {
        object Disconnected : ConnectionState()
        object Connecting : ConnectionState()
        object Connected : ConnectionState()
        data class Error(val message: String) : ConnectionState()
    }
}

