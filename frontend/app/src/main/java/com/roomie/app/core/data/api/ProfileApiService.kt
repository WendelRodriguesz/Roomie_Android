package com.roomie.app.core.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Body
import retrofit2.http.Path
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import com.roomie.app.feature.profile.data.remote.dto.AtualizarUsuarioBasicoRequest
import com.roomie.app.feature.profile.data.remote.dto.UsuarioOfertanteDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto


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

    @Multipart
    @POST("api/usuarioInteressado/uploadFotoDePerfil/{id_usuario}")
    suspend fun uploadFotoInteressado(
        @Path("id_usuario") idUsuario: Long,
        @Header("Authorization") authHeader: String,
        @Part file: MultipartBody.Part
    ): Response<String>

    @Multipart
    @POST("api/usuarioOfertante/uploadFotoDePerfil/{id_usuario}")
    suspend fun uploadFotoOfertante(
        @Path("id_usuario") idUsuario: Long,
        @Header("Authorization") authHeader: String,
        @Part file: MultipartBody.Part
    ): Response<String>
}
