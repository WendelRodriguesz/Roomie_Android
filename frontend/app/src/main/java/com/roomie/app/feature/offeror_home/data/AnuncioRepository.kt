package com.roomie.app.feature.offeror_home.data

import com.roomie.app.core.data.api.AnuncioApiService
import com.roomie.app.feature.offeror_home.data.remote.dto.AtualizarAnuncioRequest
import com.roomie.app.feature.offeror_home.data.remote.dto.CadastrarAnuncioRequest
import com.roomie.app.feature.offeror_home.model.Anuncio
import com.roomie.app.feature.offeror_home.model.toAnuncio
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class AnuncioRepository(
    private val apiService: AnuncioApiService
) {
    suspend fun visualizarAnuncio(
        anuncioId: Long,
        token: String
    ): Result<Anuncio> {
        return try {
            val authHeader = "Bearer $token"
            val response = apiService.visualizarAnuncio(anuncioId, authHeader)
            
            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                val anuncio = dto.toAnuncio()
                Result.success(anuncio)
            } else {
                val errorBody = response.errorBody()?.string()
                val httpCode = response.code()
                
                android.util.Log.e("AnuncioRepository", "Erro ao visualizar anúncio: código $httpCode")
                
                val errorMessage = when (httpCode) {
                    403 -> "Acesso negado (403). Verifique se o token está válido e se você tem permissão para visualizar este anúncio."
                    401 -> "Não autorizado (401). Faça login novamente."
                    404 -> "Anúncio não encontrado (404)."
                    500 -> "Erro interno do servidor (500). Tente novamente mais tarde."
                    else -> errorBody?.takeIf { it.isNotBlank() }
                        ?: "Erro ao visualizar anúncio (código: $httpCode)"
                }
                
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            android.util.Log.e("AnuncioRepository", "Exceção ao visualizar anúncio", e)
            Result.failure(e)
        }
    }

    suspend fun cadastrarAnuncio(
        userId: Long,
        token: String,
        request: CadastrarAnuncioRequest
    ): Result<Anuncio> {
        return try {
            val authHeader = "Bearer $token"
            val response = apiService.cadastrarAnuncio(userId, authHeader, request)
            
            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                val anuncio = dto.toAnuncio()
                Result.success(anuncio)
            } else {
                val errorBody = response.errorBody()?.string()
                val httpCode = response.code()
                
                android.util.Log.e("AnuncioRepository", "Erro ao cadastrar anúncio: código $httpCode")
                
                val errorMessage = when (httpCode) {
                    400 -> "Dados inválidos. Verifique se todos os campos estão preenchidos corretamente.${if (errorBody != null) "\n$errorBody" else ""}"
                    401 -> "Não autorizado (401). Faça login novamente."
                    403 -> "Acesso negado (403). Você não tem permissão para cadastrar anúncio."
                    500 -> "Erro interno do servidor (500). Tente novamente mais tarde."
                    else -> errorBody?.takeIf { it.isNotBlank() }
                        ?: "Erro ao cadastrar anúncio (código: $httpCode)"
                }
                
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            android.util.Log.e("AnuncioRepository", "Exceção ao cadastrar anúncio", e)
            Result.failure(e)
        }
    }

    suspend fun atualizarAnuncio(
        anuncioId: Long,
        token: String,
        request: AtualizarAnuncioRequest
    ): Result<Anuncio> {
        return try {
            val authHeader = "Bearer $token"
            val response = apiService.atualizarAnuncio(idAnuncio = anuncioId, authHeader = authHeader, body = request)
            
            if (response.isSuccessful && response.body() != null) {
                val anuncio = response.body()!!.toAnuncio()
                Result.success(anuncio)
            } else {
                val errorBody = response.errorBody()?.string()
                val httpCode = response.code()
                
                android.util.Log.e("AnuncioRepository", "Erro ao atualizar anúncio: código $httpCode")
                
                val errorMessage = when (httpCode) {
                    400 -> "Dados inválidos. Verifique se todos os campos estão preenchidos corretamente.${if (errorBody != null) "\n$errorBody" else ""}"
                    401 -> "Não autorizado (401). Faça login novamente."
                    403 -> "Acesso negado (403). Você não tem permissão para atualizar este anúncio."
                    404 -> "Anúncio não encontrado (404)."
                    500 -> "Erro interno do servidor (500). Tente novamente mais tarde."
                    else -> errorBody?.takeIf { it.isNotBlank() }
                        ?: "Erro ao atualizar anúncio (código: $httpCode)"
                }
                
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            android.util.Log.e("AnuncioRepository", "Exceção ao atualizar anúncio", e)
            Result.failure(e)
        }
    }

    suspend fun pausarAnuncio(
        anuncioId: Long,
        token: String
    ): Result<Anuncio> {
        return try {
            val authHeader = "Bearer $token"
            val response = apiService.pausarAnuncio(idAnuncio = anuncioId, authHeader = authHeader)
            
            if (response.isSuccessful && response.body() != null) {
                val anuncio = response.body()!!.toAnuncio()
                Result.success(anuncio)
            } else {
                val errorMessage = response.errorBody()?.string()
                    ?: "Erro ao pausar anúncio (código: ${response.code()})"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun reativarAnuncio(
        anuncioId: Long,
        token: String
    ): Result<Anuncio> {
        return try {
            val authHeader = "Bearer $token"
            val response = apiService.reativarAnuncio(idAnuncio = anuncioId, authHeader = authHeader)
            
            if (response.isSuccessful && response.body() != null) {
                val anuncio = response.body()!!.toAnuncio()
                Result.success(anuncio)
            } else {
                val errorMessage = response.errorBody()?.string()
                    ?: "Erro ao reativar anúncio (código: ${response.code()})"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadNovaFoto(
        userId: Long,
        token: String,
        bytes: ByteArray,
        fileName: String,
        mimeType: String?
    ): Result<String> {
        return try {
            val authHeader = "Bearer $token"
            val body = bytes.toRequestBody(mimeType?.toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("file", fileName, body)
            
            val response = apiService.uploadNovaFoto(userId, authHeader, part)
            
            if (response.isSuccessful) {
                val url = response.body()
                if (url.isNullOrBlank()) {
                    Result.failure(Exception("Resposta vazia no upload"))
                } else {
                    Result.success(url)
                }
            } else {
                val errorMessage = response.errorBody()?.string()
                    ?: "Erro ao fazer upload da foto (código: ${response.code()})"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deletarFoto(
        userId: Long,
        token: String,
        urlFoto: String
    ): Result<Unit> {
        return try {
            val authHeader = "Bearer $token"
            val response = apiService.deletarFoto(userId, authHeader, urlFoto)
            
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                val httpCode = response.code()
                
                android.util.Log.e("AnuncioRepository", "Erro ao deletar foto: código $httpCode")
                
                val errorMessage = when (httpCode) {
                    400 -> "Requisição inválida ao deletar a foto."
                    401 -> "Não autorizado (401). Faça login novamente."
                    403 -> "Acesso negado (403). Você não tem permissão para deletar esta foto."
                    404 -> "Foto não encontrada (404)."
                    else -> errorBody ?: "Erro ao deletar foto (código: $httpCode)"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            android.util.Log.e("AnuncioRepository", "Exceção ao deletar foto", e)
            Result.failure(e)
        }
    }
}
