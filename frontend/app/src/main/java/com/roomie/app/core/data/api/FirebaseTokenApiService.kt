package com.roomie.app.core.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

data class FirebaseTokenRequest(
    val firebase_token: String
)

interface FirebaseTokenApiService {
    @POST("api/usuarioInteressado/cadastrarFirebaseToken/{id_usuario}")
    suspend fun cadastrarTokenInteressado(
        @Path("id_usuario") idUsuario: Long,
        @Header("Authorization") token: String,
        @Body request: FirebaseTokenRequest
    ): Response<Unit>

    @POST("api/usuarioOfertante/cadastrarFirebaseToken/{id_usuario}")
    suspend fun cadastrarTokenOfertante(
        @Path("id_usuario") idUsuario: Long,
        @Header("Authorization") token: String,
        @Body request: FirebaseTokenRequest
    ): Response<Unit>
}

