package com.roomie.app.core.data.api

import com.roomie.app.feature.match.data.model.MatchCandidateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MatchApiService {
    @GET("api/match/buscarCandidatos")
    suspend fun buscarCandidatos(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("id_usuario_interessado") idUsuarioInteressado: Long,
        @Header("Authorization") authHeader: String
    ): Response<MatchCandidateResponse>
}

