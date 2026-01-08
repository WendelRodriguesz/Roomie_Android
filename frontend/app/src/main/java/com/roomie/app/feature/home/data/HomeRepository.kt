package com.roomie.app.feature.home.data

import com.roomie.app.core.data.api.HomeApiService
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.feature.home.data.remote.dto.toListingCard
import com.roomie.app.feature.home.data.remote.dto.toListingDetail
import com.roomie.app.feature.home.model.ListingDetail
import com.roomie.app.feature.match.model.ListingCard

class HomeRepository(
    private val api: HomeApiService = RetrofitClient.homeApiService
) {
    suspend fun listarAnuncios(): Result<List<ListingCard>> {
        return try {
            val token = AuthSession.token
            if (token.isNullOrBlank()) {
                Result.failure(Exception("Token de autenticação não encontrado. Faça login novamente."))
            } else {
                val authHeader = "Bearer $token"
                val response = api.listarAnuncios(authHeader)

                if (response.isSuccessful && response.body() != null) {
                    val anuncios = response.body()!!
                    val listings = anuncios.map { it.toListingCard() }
                    Result.success(listings)
                } else {
                    val errorCode = response.code()
                    val errorMessage = response.errorBody()?.string()
                        ?: "Erro ao buscar anúncios (código: $errorCode). Tente novamente."
                    Result.failure(Exception(errorMessage))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun buscarAnuncioPorId(idAnuncio: Int): Result<ListingDetail> {
        return try {
            val token = AuthSession.token
            if (token.isNullOrBlank()) {
                Result.failure(Exception("Token de autenticação não encontrado. Faça login novamente."))
            } else {
                val authHeader = "Bearer $token"
                val response = api.buscarAnuncioPorId(idAnuncio, authHeader)

                if (response.isSuccessful && response.body() != null) {
                    val anuncio = response.body()!!
                    val listingDetail = anuncio.toListingDetail()
                    Result.success(listingDetail)
                } else {
                    val errorCode = response.code()
                    val errorMessage = response.errorBody()?.string()
                        ?: "Erro ao buscar anúncio (código: $errorCode). Tente novamente."
                    Result.failure(Exception(errorMessage))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

