package com.roomie.app.core.data.api

import com.roomie.app.feature.match.data.model.MatchCandidateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

import retrofit2.http.POST

interface MatchApiService {
    @GET("api/match/buscarCandidatos")
    suspend fun buscarCandidatos(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("id_usuario_interessado") idUsuarioInteressado: Long,
        @Header("Authorization") authHeader: String
    ): Response<MatchCandidateResponse>
    
    @POST("api/match/enviarLike")
    suspend fun enviarLike(
        @Query("id_usuario_interessado") idUsuarioInteressado: Long,
        @Query("id_usuario_ofertante") idUsuarioOfertante: Long,
        @Header("Authorization") authHeader: String
    ): Response<Unit>
    
    @GET("api/match/visualizarMeusLikes")
    suspend fun visualizarMeusLikes(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("id_ofertante") idOfertante: Long,
        @Header("Authorization") authHeader: String
    ): Response<com.roomie.app.feature.notifications.data.model.MatchResponse>
}

