package com.roomie.app.core.data.api

import com.roomie.app.feature.profile.data.remote.dto.AtualizarUsuarioBasicoRequest
import com.roomie.app.feature.profile.data.remote.dto.InteressesInteressadoRequest
import com.roomie.app.feature.profile.data.remote.dto.InteressesInteressadoUpdateRequest
import com.roomie.app.feature.profile.data.remote.dto.InteressesOfertanteRequest
import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioOfertanteDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ProfileApiService {
    
    // Usuario Interessado
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
        
    @Multipart
    @POST("api/usuarioInteressado/uploadFotoDePerfil/{id_usuario}")
    suspend fun uploadFotoInteressado(
        @Path("id_usuario") idUsuario: Long,
        @Header("Authorization") authHeader: String,
        @Part file: MultipartBody.Part
    ): Response<String>

    // Interesses Interessados
    @POST("api/interessesInteressados/cadastrar/{id_interesses}")
    suspend fun createInteressesInteressado(
        @Path("id_interesses") idInteresses: Long,
        @Header("Authorization") authHeader: String,
        @Body body: InteressesInteressadoRequest,
    ): Response<Unit>

    @PATCH("api/interessesInteressados/atualizar/{id_interesses}")
    suspend fun updateInteressesInteressado(
        @Path("id_interesses") idInteresses: Long,
        @Header("Authorization") authHeader: String,
        @Body body: InteressesInteressadoUpdateRequest,
    ): Response<Unit>

    // Usuario Ofertante
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
    @POST("api/usuarioOfertante/uploadFotoDePerfil/{id_usuario}")
    suspend fun uploadFotoOfertante(
        @Path("id_usuario") idUsuario: Long,
        @Header("Authorization") authHeader: String,
        @Part file: MultipartBody.Part
    ): Response<String>

    // Interesses Ofertantes
    @POST("api/interessesOfertantes/cadastrar/{id_interesses}")
    suspend fun createInteressesOfertante(
        @Path("id_interesses") idInteresses: Long,
        @Header("Authorization") authHeader: String,
        @Body body: InteressesOfertanteRequest,
    ): Response<Unit>

    @PATCH("api/interessesOfertantes/atualizar/{id_interesses}")
    suspend fun updateInteressesOfertante(
        @Path("id_interesses") idInteresses: Long,
        @Header("Authorization") authHeader: String,
        @Body body: InteressesOfertanteRequest,
    ): Response<Unit>
}
