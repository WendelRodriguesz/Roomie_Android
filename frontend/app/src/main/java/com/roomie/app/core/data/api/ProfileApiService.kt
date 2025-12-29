package com.roomie.app.core.data.api

import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.PUT
import com.roomie.app.feature.profile.data.remote.dto.AtualizarUsuarioInteressadoRequest


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
        @Body body: AtualizarUsuarioInteressadoRequest,
    ): Response<UsuarioInteressadoDto>
}
