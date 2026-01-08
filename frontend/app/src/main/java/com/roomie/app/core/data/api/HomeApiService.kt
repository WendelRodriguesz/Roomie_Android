package com.roomie.app.core.data.api

import com.roomie.app.feature.home.data.remote.dto.AnuncioResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeApiService {
    @GET("api/anuncio/visualizarTodos")
    suspend fun listarAnuncios(
        @Header("Authorization") authHeader: String
    ): Response<List<AnuncioResponseDto>>
}


