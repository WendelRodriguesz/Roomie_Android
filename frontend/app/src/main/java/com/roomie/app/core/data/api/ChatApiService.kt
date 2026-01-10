package com.roomie.app.core.data.api

import com.roomie.app.feature.chat.data.remote.dto.ChatDto
import com.roomie.app.feature.chat.data.remote.dto.MensagemDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioOfertanteDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ChatApiService {
    @GET("api/chat/visualizarMeusChats/{id_usuario}")
    suspend fun visualizarMeusChats(
        @Path("id_usuario") idUsuario: Long,
        @Header("Authorization") authHeader: String
    ): Response<List<ChatDto>>
    
    @GET("api/mensagem/visualizarMensagens/{id_chat}")
    suspend fun visualizarMensagens(
        @Path("id_chat") idChat: Long,
        @Header("Authorization") authHeader: String
    ): Response<List<MensagemDto>>
    
    @GET("api/usuarioInteressado/visualizar/{id_interessado}")
    suspend fun getUsuarioInteressado(
        @Path("id_interessado") idInteressado: Long,
        @Header("Authorization") authHeader: String
    ): Response<UsuarioInteressadoDto>
    
    @GET("api/usuarioOfertante/visualizar/{id_ofertante}")
    suspend fun getUsuarioOfertante(
        @Path("id_ofertante") idOfertante: Long,
        @Header("Authorization") authHeader: String
    ): Response<UsuarioOfertanteDto>
}