package com.roomie.app.feature.chat.data

import com.roomie.app.core.data.api.ChatApiService
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.feature.chat.data.remote.dto.ChatDto
import com.roomie.app.feature.chat.model.AnuncioInfo
import com.roomie.app.feature.chat.model.ChatListItem
import com.roomie.app.feature.chat.model.ChatUserDetail
import com.roomie.app.feature.chat.model.Mensagem
import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioOfertanteDto

class ChatRepository(
    private val api: ChatApiService = RetrofitClient.chatApiService
) {
    suspend fun visualizarMeusChats(userId: Long): Result<List<ChatDto>> {
        return try {
            val token = AuthSession.token
            if (token.isNullOrBlank()) {
                Result.failure(Exception("Token de autenticação não encontrado. Faça login novamente."))
            } else {
                val authHeader = "Bearer $token"
                val response = api.visualizarMeusChats(userId, authHeader)

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorCode = response.code()
                    val errorMessage = response.errorBody()?.string()
                        ?: "Erro ao buscar chats (código: $errorCode). Tente novamente."
                    Result.failure(Exception(errorMessage))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUsuarioInteressado(idInteressado: Long): Result<UsuarioInteressadoDto> {
        return try {
            val token = AuthSession.token
            if (token.isNullOrBlank()) {
                Result.failure(Exception("Token de autenticação não encontrado. Faça login novamente."))
            } else {
                val authHeader = "Bearer $token"
                val response = api.getUsuarioInteressado(idInteressado, authHeader)

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorCode = response.code()
                    val errorMessage = response.errorBody()?.string()
                        ?: "Erro ao buscar usuário interessado (código: $errorCode). Tente novamente."
                    Result.failure(Exception(errorMessage))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUsuarioOfertante(idOfertante: Long): Result<UsuarioOfertanteDto> {
        return try {
            val token = AuthSession.token
            if (token.isNullOrBlank()) {
                Result.failure(Exception("Token de autenticação não encontrado. Faça login novamente."))
            } else {
                val authHeader = "Bearer $token"
                val response = api.getUsuarioOfertante(idOfertante, authHeader)

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorCode = response.code()
                    val errorMessage = response.errorBody()?.string()
                        ?: "Erro ao buscar usuário ofertante (código: $errorCode). Tente novamente."
                    Result.failure(Exception(errorMessage))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun visualizarMensagens(idChat: Long): Result<List<Mensagem>> {
        return try {
            val token = AuthSession.token
            val userId = AuthSession.userId
            if (token.isNullOrBlank() || userId == null) {
                Result.failure(Exception("Token de autenticação não encontrado. Faça login novamente."))
            } else {
                val authHeader = "Bearer $token"
                val response = api.visualizarMensagens(idChat, authHeader)

                if (response.isSuccessful && response.body() != null) {
                    val mensagensDto = response.body()!!
                    val mensagens = mensagensDto.map { dto ->
                        Mensagem(
                            id = dto.id,
                            idChat = dto.id_chat,
                            idRemetente = dto.id_remetente,
                            idDestinatario = dto.id_destinatario,
                            conteudo = dto.conteudo,
                            enviadaEm = dto.enviada_em,
                            isMine = dto.id_remetente == userId
                        )
                    }
                    Result.success(mensagens)
                } else {
                    val errorCode = response.code()
                    val errorMessage = response.errorBody()?.string()
                        ?: "Erro ao buscar mensagens (código: $errorCode). Tente novamente."
                    Result.failure(Exception(errorMessage))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun carregarListaChats(userId: Long): Result<List<ChatListItem>> {
        return try {
            val chatsResult = visualizarMeusChats(userId)
            chatsResult.fold(
                onSuccess = { chats ->
                    val chatItems = mutableListOf<ChatListItem>()
                    
                    for (chat in chats) {
                        val isOfertante = chat.id_ofertante == userId
                        val otherUserId = if (isOfertante) chat.id_interessado else chat.id_ofertante
                        
                        if (isOfertante) {
                            getUsuarioInteressado(otherUserId).fold(
                                onSuccess = { userDto ->
                                    val chatItem = ChatListItem(
                                        chatId = chat.id,
                                        otherUserId = userDto.id,
                                        otherUserName = userDto.nome,
                                        otherUserPhotoUrl = userDto.foto_de_perfil,
                                        otherUserAge = userDto.idade,
                                        otherUserCity = userDto.cidade,
                                        otherUserOccupation = userDto.ocupacao,
                                        otherUserBio = userDto.bio,
                                        usadoEm = chat.usado_em,
                                        isOfertante = true,
                                        anuncioTitulo = null
                                    )
                                    chatItems.add(chatItem)
                                },
                                onFailure = {
                                }
                            )
                        } else {
                            getUsuarioOfertante(otherUserId).fold(
                                onSuccess = { userDto ->
                                    val chatItem = ChatListItem(
                                        chatId = chat.id,
                                        otherUserId = userDto.id,
                                        otherUserName = userDto.nome,
                                        otherUserPhotoUrl = userDto.foto_de_perfil,
                                        otherUserAge = userDto.idade,
                                        otherUserCity = userDto.cidade,
                                        otherUserOccupation = userDto.ocupacao,
                                        otherUserBio = userDto.bio,
                                        usadoEm = chat.usado_em,
                                        isOfertante = false,
                                        anuncioTitulo = userDto.anuncio?.titulo
                                    )
                                    chatItems.add(chatItem)
                                },
                                onFailure = {
                                }
                            )
                        }
                    }
                    
                    Result.success(chatItems)
                },
                onFailure = { error ->
                    Result.failure(error)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun carregarDetalhesUsuario(
        userId: Long,
        isOfertante: Boolean
    ): Result<ChatUserDetail> {
        return try {
            if (isOfertante) {
                getUsuarioOfertante(userId).fold(
                    onSuccess = { userDto ->
                        val detail = ChatUserDetail(
                            id = userDto.id,
                            nome = userDto.nome,
                            email = userDto.email,
                            dataDeNascimento = userDto.data_de_nascimento,
                            idade = userDto.idade,
                            cidade = userDto.cidade,
                            ocupacao = userDto.ocupacao,
                            bio = userDto.bio,
                            genero = userDto.genero,
                            fotoDePerfil = userDto.foto_de_perfil,
                            isOfertante = true,
                            anuncio = userDto.anuncio?.let { anuncio ->
                                AnuncioInfo(
                                    id = anuncio.id,
                                    titulo = anuncio.titulo,
                                    descricao = anuncio.descricao,
                                    rua = anuncio.rua,
                                    numero = anuncio.numero,
                                    bairro = anuncio.bairro,
                                    cidade = anuncio.cidade,
                                    estado = anuncio.estado,
                                    valorAluguel = anuncio.valor_aluguel,
                                    valorContas = anuncio.valor_contas,
                                    vagasDisponiveis = anuncio.vagas_disponiveis,
                                    tipoImovel = anuncio.tipo_imovel,
                                    fotos = anuncio.fotos,
                                    comodos = anuncio.comodos
                                )
                            }
                        )
                        Result.success(detail)
                    },
                    onFailure = { error ->
                        Result.failure(error)
                    }
                )
            } else {
                getUsuarioInteressado(userId).fold(
                    onSuccess = { userDto ->
                        val detail = ChatUserDetail(
                            id = userDto.id,
                            nome = userDto.nome,
                            email = userDto.email,
                            dataDeNascimento = userDto.data_de_nascimento,
                            idade = userDto.idade,
                            cidade = userDto.cidade,
                            ocupacao = userDto.ocupacao,
                            bio = userDto.bio,
                            genero = userDto.genero,
                            fotoDePerfil = userDto.foto_de_perfil,
                            isOfertante = false,
                            anuncio = null
                        )
                        Result.success(detail)
                    },
                    onFailure = { error ->
                        Result.failure(error)
                    }
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

