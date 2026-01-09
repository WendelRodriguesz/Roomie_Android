package com.roomie.app.feature.offeror_home.data

import com.roomie.app.core.data.api.AnuncioApiService
import com.roomie.app.feature.offeror_home.data.remote.dto.AtualizarAnuncioRequest
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
            android.util.Log.d("AnuncioRepository", "🔍 Iniciando busca de anúncio")
            android.util.Log.d("AnuncioRepository", "  - ID do anúncio: $anuncioId")
            android.util.Log.d("AnuncioRepository", "  - Token (primeiros 20 chars): ${token.take(20)}...")
            android.util.Log.d("AnuncioRepository", "  - Auth Header: $authHeader")
            
            val response = apiService.visualizarAnuncio(anuncioId, authHeader)
            
            android.util.Log.d("AnuncioRepository", "📡 Resposta recebida:")
            android.util.Log.d("AnuncioRepository", "  - Código HTTP: ${response.code()}")
            android.util.Log.d("AnuncioRepository", "  - Sucesso: ${response.isSuccessful}")
            android.util.Log.d("AnuncioRepository", "  - Tem body: ${response.body() != null}")
            
            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                android.util.Log.d("AnuncioRepository", "✅ Anúncio recebido com sucesso:")
                android.util.Log.d("AnuncioRepository", "  - Título: ${dto.titulo}")
                android.util.Log.d("AnuncioRepository", "  - Status: ${dto.status}")
                android.util.Log.d("AnuncioRepository", "  - ID: ${dto.id}")
                val anuncio = dto.toAnuncio()
                Result.success(anuncio)
            } else {
                val errorBody = response.errorBody()?.string()
                val httpCode = response.code()
                val httpMessage = response.message()
                
                android.util.Log.e("AnuncioRepository", "❌ ERRO na resposta HTTP:")
                android.util.Log.e("AnuncioRepository", "  - Código: $httpCode")
                android.util.Log.e("AnuncioRepository", "  - Mensagem: $httpMessage")
                android.util.Log.e("AnuncioRepository", "  - Error Body: ${errorBody ?: "(vazio)"}")
                android.util.Log.e("AnuncioRepository", "  - Headers: ${response.headers()}")
                
                // Mensagem de erro mais descritiva baseada no código HTTP
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
            android.util.Log.e("AnuncioRepository", "💥 EXCEÇÃO ao visualizar anúncio:", e)
            android.util.Log.e("AnuncioRepository", "  - Tipo: ${e.javaClass.simpleName}")
            android.util.Log.e("AnuncioRepository", "  - Mensagem: ${e.message}")
            e.printStackTrace()
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
            android.util.Log.d("AnuncioRepository", "🔄 Atualizando anúncio - ID: $anuncioId")
            android.util.Log.d("AnuncioRepository", "  - Título: ${request.titulo}")
            android.util.Log.d("AnuncioRepository", "  - Comodos: ${request.comodos}")
            android.util.Log.d("AnuncioRepository", "  - Valor Aluguel: ${request.valorAluguel}")
            android.util.Log.d("AnuncioRepository", "  - Tipo Imóvel: ${request.tipo_imovel}")
            
            val response = apiService.atualizarAnuncio(idAnuncio = anuncioId, authHeader = authHeader, body = request)
            
            android.util.Log.d("AnuncioRepository", "📡 Resposta recebida:")
            android.util.Log.d("AnuncioRepository", "  - Código HTTP: ${response.code()}")
            android.util.Log.d("AnuncioRepository", "  - Sucesso: ${response.isSuccessful}")
            
            if (response.isSuccessful && response.body() != null) {
                val anuncio = response.body()!!.toAnuncio()
                android.util.Log.d("AnuncioRepository", "✅ Anúncio atualizado com sucesso")
                Result.success(anuncio)
            } else {
                val errorBody = response.errorBody()?.string()
                val httpCode = response.code()
                val httpMessage = response.message()
                
                android.util.Log.e("AnuncioRepository", "❌ ERRO ao atualizar anúncio:")
                android.util.Log.e("AnuncioRepository", "  - Código: $httpCode")
                android.util.Log.e("AnuncioRepository", "  - Mensagem: $httpMessage")
                android.util.Log.e("AnuncioRepository", "  - Error Body: ${errorBody ?: "(vazio)"}")
                
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
            android.util.Log.e("AnuncioRepository", "💥 EXCEÇÃO ao atualizar anúncio:", e)
            android.util.Log.e("AnuncioRepository", "  - Tipo: ${e.javaClass.simpleName}")
            android.util.Log.e("AnuncioRepository", "  - Mensagem: ${e.message}")
            e.printStackTrace()
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
            android.util.Log.d("AnuncioRepository", "🗑️ Deletando foto")
            android.util.Log.d("AnuncioRepository", "  - User ID: $userId")
            android.util.Log.d("AnuncioRepository", "  - URL Foto: $urlFoto")
            
            val response = apiService.deletarFoto(userId, authHeader, urlFoto)
            
            android.util.Log.d("AnuncioRepository", "📡 Resposta recebida:")
            android.util.Log.d("AnuncioRepository", "  - Código HTTP: ${response.code()}")
            android.util.Log.d("AnuncioRepository", "  - Sucesso: ${response.isSuccessful}")
            
            if (response.isSuccessful) {
                android.util.Log.d("AnuncioRepository", "✅ Foto deletada com sucesso")
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                val httpCode = response.code()
                val httpMessage = response.message()
                
                android.util.Log.e("AnuncioRepository", "❌ ERRO ao deletar foto:")
                android.util.Log.e("AnuncioRepository", "  - Código: $httpCode")
                android.util.Log.e("AnuncioRepository", "  - Mensagem: $httpMessage")
                android.util.Log.e("AnuncioRepository", "  - Error Body: ${errorBody ?: "(vazio)"}")
                
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
            android.util.Log.e("AnuncioRepository", "💥 EXCEÇÃO ao deletar foto:", e)
            android.util.Log.e("AnuncioRepository", "  - Tipo: ${e.javaClass.simpleName}")
            android.util.Log.e("AnuncioRepository", "  - Mensagem: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
