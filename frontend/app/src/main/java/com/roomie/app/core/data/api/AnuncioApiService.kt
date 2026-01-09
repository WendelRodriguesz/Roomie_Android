package com.roomie.app.core.data.api

import com.roomie.app.feature.offeror_home.data.remote.dto.AnuncioResponseDto
import com.roomie.app.feature.offeror_home.data.remote.dto.AtualizarAnuncioRequest
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

interface AnuncioApiService {
    @GET("api/anuncio/visualizar/{id_anuncio}")
    suspend fun visualizarAnuncio(
        @Path("id_anuncio") idAnuncio: Long,
        @Header("Authorization") authHeader: String
    ): Response<AnuncioResponseDto>

    @PATCH("api/anuncio/atualizar/{id_anuncio}")
    suspend fun atualizarAnuncio(
        @Path("id_anuncio") idAnuncio: Long,
        @Header("Authorization") authHeader: String,
        @Body body: AtualizarAnuncioRequest
    ): Response<AnuncioResponseDto>

    @PATCH("api/anuncio/pausar/{id_anuncio}")
    suspend fun pausarAnuncio(
        @Path("id_anuncio") idAnuncio: Long,
        @Header("Authorization") authHeader: String
    ): Response<AnuncioResponseDto>

    @PATCH("api/anuncio/reativar/{id_anuncio}")
    suspend fun reativarAnuncio(
        @Path("id_anuncio") idAnuncio: Long,
        @Header("Authorization") authHeader: String
    ): Response<AnuncioResponseDto>

    @Multipart
    @POST("api/anuncio/uploadNovaFoto/{id_usuario}")
    suspend fun uploadNovaFoto(
        @Path("id_usuario") idUsuario: Long,
        @Header("Authorization") authHeader: String,
        @Part file: MultipartBody.Part
    ): Response<String>
}
