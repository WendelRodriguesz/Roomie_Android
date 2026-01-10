package com.roomie.app.feature.chat.conversation.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roomie.app.core.data.websocket.StompWebSocketManager
import com.roomie.app.feature.chat.data.ChatRepository
import com.roomie.app.feature.chat.model.Mensagem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatConversationViewModel(
    private val chatId: Long,
    private val otherUserId: Long,
    private val otherUserName: String,
    private val otherUserPhotoUrl: String?,
    private val repository: ChatRepository = ChatRepository(),
    private val webSocketManager: StompWebSocketManager = StompWebSocketManager.getInstance()
) : ViewModel() {

    private val _state = MutableStateFlow(
        ChatConversationState(
            chatId = chatId,
            otherUserId = otherUserId,
            otherUserName = otherUserName,
            otherUserPhotoUrl = otherUserPhotoUrl
        )
    )
    val state: StateFlow<ChatConversationState> = _state.asStateFlow()
    
    private var messageSubscription: io.reactivex.disposables.Disposable? = null
    private var connectionJob: Job? = null

    init {
        loadHistory()
        connectWebSocket()
    }

    fun onEvent(event: ChatConversationEvent) {
        when (event) {
            is ChatConversationEvent.MessageTextChanged -> {
                _state.update { it.copy(messageText = event.text) }
            }
            ChatConversationEvent.SendMessage -> {
                sendMessage()
            }
            ChatConversationEvent.LoadHistory -> {
                loadHistory()
            }
            ChatConversationEvent.RetryConnection -> {
                connectWebSocket()
            }
            ChatConversationEvent.ClearError -> {
                _state.update { it.copy(errorMessage = null) }
            }
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingHistory = true, errorMessage = null) }
            val result = repository.visualizarMensagens(chatId)
            result.fold(
                onSuccess = { mensagens ->
                    _state.update { 
                        it.copy(
                            mensagens = mensagens.sortedWith(
                                compareBy(
                                    { parseDateTime(it.enviadaEm) },
                                    { it.id }
                                )
                            ),
                            isLoadingHistory = false
                        )
                    }
                },
                onFailure = { error ->
                    _state.update { 
                        it.copy(
                            isLoadingHistory = false,
                            errorMessage = error.message ?: "Erro ao carregar histórico de mensagens"
                        )
                    }
                }
            )
        }
    }

    private fun connectWebSocket() {
        connectionJob?.cancel()
        
        connectionJob = viewModelScope.launch {
            try {
                webSocketManager.ensureConnection()

                _state.update { 
                    it.copy(connectionState = webSocketManager.connectionState.value) 
                }

                if (webSocketManager.isConnected()) {
                    kotlinx.coroutines.delay(300)
                    subscribeToChatTopic()
                } else {
                    webSocketManager.connectionState
                        .collectLatest { connectionState ->
                            _state.update { it.copy(connectionState = connectionState) }
                            
                            when (connectionState) {
                                is StompWebSocketManager.ConnectionState.Connected -> {
                                    kotlinx.coroutines.delay(300)
                                    subscribeToChatTopic()
                                }
                                is StompWebSocketManager.ConnectionState.Error -> {
                                    _state.update { 
                                        it.copy(errorMessage = "Erro de conexão: ${connectionState.message}")
                                    }
                                }
                                else -> {}
                            }
                        }
                }
            } catch (e: Exception) {
                if (e !is kotlinx.coroutines.CancellationException) {
                    Log.e("ChatConversationViewModel", "Error in connectWebSocket: ${e.message}", e)
                }
            }
        }
    }
    
    private fun subscribeToChatTopic() {
        Log.d("ChatViewModel", "Subscribing to chat topic for chatId: $chatId")
        messageSubscription?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
        
        messageSubscription = webSocketManager.subscribeToChat(chatId) { mensagem ->
            Log.d("ChatViewModel", "Message received in callback: id=${mensagem.id}, isMine=${mensagem.isMine}, conteudo=${mensagem.conteudo.take(50)}")
            viewModelScope.launch {
                try {
                    _state.update { currentState ->
                        val mensagensAtualizadas = currentState.mensagens.toMutableList()

                        val existingById = mensagensAtualizadas.indexOfFirst { it.id == mensagem.id }
                        
                        if (existingById >= 0) {
                            Log.d("ChatViewModel", "Replacing existing message at index $existingById")
                            mensagensAtualizadas[existingById] = mensagem
                        } else if (mensagem.isMine) {
                            val optimisticIndex = mensagensAtualizadas.indexOfFirst { 
                                it.id < 0 && it.isMine && it.conteudo == mensagem.conteudo
                            }
                            
                            if (optimisticIndex >= 0) {
                                Log.d("ChatViewModel", "Replacing optimistic message at index $optimisticIndex")
                                mensagensAtualizadas[optimisticIndex] = mensagem
                            } else {
                                Log.d("ChatViewModel", "Adding new message (mine) to list, current size: ${mensagensAtualizadas.size}")
                                mensagensAtualizadas.add(mensagem)
                            }
                        } else {
                            Log.d("ChatViewModel", "Adding new message (other user) to list, current size: ${mensagensAtualizadas.size}")
                            mensagensAtualizadas.add(mensagem)
                        }

                        val mensagensOrdenadas = mensagensAtualizadas.sortedWith(
                            compareBy(
                                { parseDateTime(it.enviadaEm) },
                                { it.id }
                            )
                        )
                        
                        Log.d("ChatViewModel", "Updated messages list size: ${mensagensOrdenadas.size}")
                        currentState.copy(
                            mensagens = mensagensOrdenadas
                        )
                    }
                } catch (e: Exception) {
                    Log.e("ChatViewModel", "Error processing received message: ${e.message}", e)
                    e.printStackTrace()
                }
            }
        }
        
        if (messageSubscription == null) {
            Log.e("ChatViewModel", "Failed to subscribe to chat $chatId")
        } else {
            Log.d("ChatViewModel", "Successfully subscribed to chat $chatId")
        }
    }

    private fun sendMessage() {
        val messageText = _state.value.messageText.trim()
        if (messageText.isEmpty()) {
            return
        }

        val success = webSocketManager.sendMessage(
            chatId = chatId,
            destinatarioId = otherUserId,
            conteudo = messageText
        )

        if (success) {

            _state.update { it.copy(messageText = "") }

            val tempId = -(System.currentTimeMillis())
            val userId = com.roomie.app.core.data.session.AuthSession.userId ?: return
            
            _state.update { currentState ->
                val mensagensAtualizadas = currentState.mensagens.toMutableList()

                val timestampOtimista = if (mensagensAtualizadas.isNotEmpty()) {
                    val ultimaMensagem = mensagensAtualizadas.maxByOrNull { parseDateTime(it.enviadaEm) }
                    val ultimoTimestamp = ultimaMensagem?.let { parseDateTime(it.enviadaEm) } 
                        ?: java.time.Instant.now()

                    ultimoTimestamp.plusSeconds(1)
                } else {

                    java.time.Instant.now()
                }

                val localDateTime = java.time.LocalDateTime.ofInstant(
                    timestampOtimista, 
                    java.time.ZoneId.systemDefault()
                )
                val timestampFormatado = localDateTime.format(
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                )
                
                val mensagemOtimista = Mensagem(
                    id = tempId,
                    idChat = chatId,
                    idRemetente = userId,
                    idDestinatario = otherUserId,
                    conteudo = messageText,
                    enviadaEm = timestampFormatado,
                    isMine = true
                )

                mensagensAtualizadas.add(mensagemOtimista)

                val mensagensOrdenadas = mensagensAtualizadas.sortedWith(
                    compareBy(
                        { parseDateTime(it.enviadaEm) },
                        { it.id }
                    )
                )
                currentState.copy(
                    mensagens = mensagensOrdenadas
                )
            }
        } else {
            _state.update { 
                it.copy(errorMessage = "Erro ao enviar mensagem. Verifique sua conexão.")
            }
        }
    }

    private fun parseDateTime(dateString: String): java.time.Instant {
        return try {

            try {
                return java.time.Instant.parse(dateString)
            } catch (e: Exception) {

            }
            
            val formatos = listOf(
                "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
                "yyyy-MM-dd'T'HH:mm:ss.SSS",
                "yyyy-MM-dd'T'HH:mm:ss.S",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm"
            )
            
            for (formato in formatos) {
                try {
                    val formatter = java.time.format.DateTimeFormatter.ofPattern(formato)
                    val localDateTime = java.time.LocalDateTime.parse(dateString, formatter)
                    return localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()
                } catch (e: java.time.format.DateTimeParseException) {
                    continue
                }
            }

            val partes = dateString.split("T")
            if (partes.size == 2) {
                val dataParte = partes[0].split("-")
                val horaParte = partes[1].split(":").mapNotNull { it.split(".")[0].toIntOrNull() }
                
                if (dataParte.size == 3 && horaParte.size >= 2) {
                    val ano = dataParte[0].toIntOrNull() ?: 1970
                    val mes = dataParte[1].toIntOrNull() ?: 1
                    val dia = dataParte[2].toIntOrNull() ?: 1
                    val hora = horaParte[0]
                    val minuto = horaParte.getOrElse(1) { 0 }
                    val segundo = horaParte.getOrElse(2) { 0 }
                    
                    val localDateTime = java.time.LocalDateTime.of(ano, mes, dia, hora, minuto, segundo)
                    return localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()
                }
            }

            java.time.Instant.now()
        } catch (e: Exception) {
            java.time.Instant.now()
        }
    }

    override fun onCleared() {
        super.onCleared()
        connectionJob?.cancel()
        connectionJob = null

        messageSubscription?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
        messageSubscription = null
        webSocketManager.unsubscribeFromChat(chatId)
    }
}