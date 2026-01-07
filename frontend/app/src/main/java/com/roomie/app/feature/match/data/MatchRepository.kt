package com.roomie.app.feature.match.data

import com.roomie.app.core.data.api.MatchApiService
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.feature.match.data.model.MatchCandidateResponse
import com.roomie.app.feature.match.data.model.toListingCard
import com.roomie.app.feature.match.model.ListingCard

class MatchRepository(
    private val apiService: MatchApiService = RetrofitClient.matchApiService
) {
    suspend fun buscarCandidatos(
        page: Int,
        size: Int,
        idUsuarioInteressado: Long
    ): Result<MatchCandidateResponse> {
        return try {
            val token = AuthSession.token
            if (token.isNullOrBlank()) {
                return Result.failure(Exception("Token de autenticação não encontrado. Faça login novamente."))
            }
            
            val authHeader = "Bearer $token"
            val response = apiService.buscarCandidatos(page, size, idUsuarioInteressado, authHeader)
            
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorCode = response.code()
                val errorMessage = response.errorBody()?.string() 
                    ?: "Erro ao buscar candidatos (código: $errorCode). Tente novamente."
                android.util.Log.e("MatchRepository", "Erro na API: $errorCode - $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            android.util.Log.e("MatchRepository", "Exceção ao buscar candidatos", e)
            Result.failure(e)
        }
    }

    suspend fun buscarCandidatosAsListingCards(
        page: Int,
        size: Int,
        idUsuarioInteressado: Long
    ): Result<Pair<List<ListingCard>, Boolean>> {
        return buscarCandidatos(page, size, idUsuarioInteressado).map { response ->
            android.util.Log.d("MatchRepository", "Resposta recebida: ${response.content.size} candidatos, last=${response.last}")
            val listingCards = response.content.mapNotNull { candidate ->
                try {
                    candidate.toListingCard()
                } catch (e: Exception) {
                    android.util.Log.e("MatchRepository", "Erro ao mapear candidato ${candidate.id}: ${e.message}", e)
                    null
                }
            }
            android.util.Log.d("MatchRepository", "Mapeados ${listingCards.size} cards com sucesso")
            Pair(listingCards, response.last)
        }
    }
    
    suspend fun enviarLike(
        idUsuarioInteressado: Long,
        idUsuarioOfertante: Long
    ): Result<Unit> {
        return try {
            val token = AuthSession.token
            if (token.isNullOrBlank()) {
                return Result.failure(Exception("Token de autenticação não encontrado. Faça login novamente."))
            }
            
            val authHeader = "Bearer $token"
            val response = apiService.enviarLike(idUsuarioInteressado, idUsuarioOfertante, authHeader)
            
            if (response.isSuccessful) {
                android.util.Log.d("MatchRepository", "Like enviado com sucesso")
                Result.success(Unit)
            } else {
                val errorCode = response.code()
                val errorMessage = response.errorBody()?.string() 
                    ?: "Erro ao enviar like (código: $errorCode). Tente novamente."
                android.util.Log.e("MatchRepository", "Erro ao enviar like: $errorCode - $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            android.util.Log.e("MatchRepository", "Exceção ao enviar like", e)
            Result.failure(e)
        }
    }
    
    suspend fun visualizarMeusLikes(
        page: Int,
        size: Int,
        idOfertante: Long
    ): Result<com.roomie.app.feature.notifications.data.model.MatchResponse> {
        return try {
            val token = AuthSession.token
            if (token.isNullOrBlank()) {
                return Result.failure(Exception("Token de autenticação não encontrado. Faça login novamente."))
            }
            
            val authHeader = "Bearer $token"
            val response = apiService.visualizarMeusLikes(page, size, idOfertante, authHeader)
            
            if (response.isSuccessful && response.body() != null) {
                android.util.Log.d("MatchRepository", "Matches recebidos: ${response.body()!!.content.size}")
                Result.success(response.body()!!)
            } else {
                val errorCode = response.code()
                val errorMessage = response.errorBody()?.string() 
                    ?: "Erro ao buscar matches (código: $errorCode). Tente novamente."
                android.util.Log.e("MatchRepository", "Erro ao buscar matches: $errorCode - $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            android.util.Log.e("MatchRepository", "Exceção ao buscar matches", e)
            Result.failure(e)
        }
    }
}

