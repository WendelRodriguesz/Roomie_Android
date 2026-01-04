package com.roomie.app.core.data.api

import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Body
import retrofit2.http.Path
import com.roomie.app.feature.profile.data.remote.dto.AtualizarUsuarioBasicoRequest
import com.roomie.app.feature.profile.data.remote.dto.UsuarioOfertanteDto

interface ProfileApiService {

    @GET("api/usuarioInteressado/visualizar/{id}")
    suspend fun getUsuarioInteressado(
        @Path("id") id: Long,
        @Header("Authorization") authHeader: String,
    ): Response<UsuarioInteressadoDto>

    @PATCH("api/usuarioInteressado/atualizar/{id}")
    suspend fun updateUsuarioInteressado(
        @Path("id") id: Long,
        @Header("Authorization") authHeader: String,
        @Body body: AtualizarUsuarioBasicoRequest,
    ): Response<UsuarioInteressadoDto>

    @GET("api/usuarioOfertante/visualizar/{id}")
    suspend fun getUsuarioOfertante(
        @Path("id") id: Long,
        @Header("Authorization") authHeader: String,
    ): Response<UsuarioOfertanteDto>

    @PATCH("api/usuarioOfertante/atualizar/{id}")
    suspend fun updateUsuarioOfertante(
        @Path("id") id: Long,
        @Header("Authorization") authHeader: String,
        @Body body: AtualizarUsuarioBasicoRequest,
    ): Response<UsuarioOfertanteDto>
}
