package com.roomie.app.core.data.api

import com.roomie.app.feature.home.data.remote.dto.AnuncioResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApiService {
    @GET("api/anuncio/visualizarTodos")
    suspend fun listarAnuncios(
        @Header("Authorization") authHeader: String
    ): Response<List<AnuncioResponseDto>>
    
    @GET("api/anuncio/filtrar")
    suspend fun filtrarAnuncios(
        @Header("Authorization") authHeader: String,
        @Query("cidade") cidade: String? = null,
        @Query("bairro") bairro: String? = null,
        @Query("custoMin") custoMin: Float? = null,
        @Query("custoMax") custoMax: Float? = null,
        @Query("tipoImovel") tipoImovel: String? = null,
        @Query("vagasMin") vagasMin: Int? = null,
        @Query("aceitaPets") aceitaPets: Boolean? = null,
        @Query("aceitaDividirQuarto") aceitaDividirQuarto: Boolean? = null
    ): Response<List<AnuncioResponseDto>>
    
    @GET("api/anuncio/visualizar/{id_anuncio}")
    suspend fun buscarAnuncioPorId(
        @Path("id_anuncio") idAnuncio: Int,
        @Header("Authorization") authHeader: String
    ): Response<AnuncioResponseDto>
}


