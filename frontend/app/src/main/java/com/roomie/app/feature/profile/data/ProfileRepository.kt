package com.roomie.app.feature.profile.data

import com.roomie.app.core.data.api.ProfileApiService
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.preference_registration.model.UserPreferences
import com.roomie.app.feature.profile.data.remote.dto.InteressesInteressadoRequest
import com.roomie.app.feature.profile.data.remote.dto.InteressesOfertanteRequest
import com.roomie.app.feature.profile.model.UserProfile
import com.roomie.app.feature.profile.model.*

import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioOfertanteDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import com.roomie.app.feature.profile.model.toUserProfile
import com.roomie.app.feature.profile.model.toBasicUpdateRequest

class ProfileRepository(
    private val api: ProfileApiService,
) {
    suspend fun getUserProfile(
        role: ProfileRole,
        userId: Long,
        token: String,
    ): Result<UserProfile> {
        return try {
            val auth = "Bearer $token"

            if (role == ProfileRole.SEEKER) {
                val response = api.getUsuarioInteressado(userId, auth)
                if (!response.isSuccessful) {
                    return Result.failure(IllegalStateException("Erro da API: ${response.code()} ${response.message()}"))
                }
                val body = response.body()
                if (body == null) Result.failure(IllegalStateException("Resposta da API vazia"))
                else Result.success(body.toUserProfile())
            } else {
                val response = api.getUsuarioOfertante(userId, auth)
                if (!response.isSuccessful) {
                    return Result.failure(IllegalStateException("Erro da API: ${response.code()} ${response.message()}"))
                }
                val body = response.body()
                if (body == null) Result.failure(IllegalStateException("Resposta da API vazia"))
                else Result.success(body.toUserProfile())
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    suspend fun updateUserProfile(
        role: ProfileRole,
        userId: Long,
        token: String,
        profile: UserProfile,
    ): Result<UserProfile> {
        return try {
            val auth = "Bearer $token"
            val request = profile.toBasicUpdateRequest()

            if (role == ProfileRole.SEEKER) {
                val response = api.updateUsuarioInteressado(userId, auth, request)
                if (!response.isSuccessful) {
                    return Result.failure(IllegalStateException("Erro da API na atualização: ${response.code()} ${response.message()}"))
                }
                val body = response.body()
                if (body == null) Result.failure(IllegalStateException("Resposta da API vazia na atualização"))
                else Result.success(body.toUserProfile())
            } else {
                val response = api.updateUsuarioOfertante(userId, auth, request)
                if (!response.isSuccessful) {
                    return Result.failure(IllegalStateException("Erro da API na atualização: ${response.code()} ${response.message()}"))
                }
                val body = response.body()
                if (body == null) Result.failure(IllegalStateException("Resposta da API vazia na atualização"))
                else Result.success(body.toUserProfile())
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
    suspend fun uploadProfilePhoto(
        role: ProfileRole,
        userId: Long,
        token: String,
        bytes: ByteArray,
        fileName: String,
        mimeType: String?,
    ): Result<String> {
        return try {
            val auth = "Bearer $token"
            val body = bytes.toRequestBody(mimeType?.toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("file", fileName, body)

            val response = if (role == ProfileRole.SEEKER) {
                api.uploadFotoInteressado(userId, auth, part)
            } else {
                api.uploadFotoOfertante(userId, auth, part)
            }

            if (!response.isSuccessful) {
                return Result.failure(
                    IllegalStateException("Erro upload: ${response.code()} ${response.message()}")
                )
            }

            val url = response.body()
            if (url.isNullOrBlank()) {
                Result.failure(IllegalStateException("Resposta vazia no upload"))
            } else {
                Result.success(url)
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    suspend fun upsertPreferences(
        role: ProfileRole,
        userId: Long,
        token: String,
        hasExisting: Boolean,
        prefs: UserPreferences,
    ): Result<Unit> {
        return try {
            val auth = "Bearer $token"

            val response = if (role == ProfileRole.SEEKER) {
                val body = InteressesInteressadoRequest(
                    frequencia_festas = prefs.partyFrequency,
                    habitos_limpeza = prefs.cleaningHabit,
                    aceita_pets = prefs.acceptsPets,
                    horario_sono = prefs.sleepRoutine,
                    orcamento_min = (prefs.budget.minBudget ?: 0).toFloat(),
                    orcamento_max = (prefs.budget.maxBudget ?: 0).toFloat(),
                    aceita_dividir_quarto = prefs.acceptsRoomSharing,
                    fumante = prefs.isSmoker,
                    consome_bebidas_alcoolicas = prefs.drinksAlcohol,
                )

                if (hasExisting) api.updateInteressesInteressado(userId, auth, body)
                else api.createInteressesInteressado(userId, auth, body)

            } else {
                val body = InteressesOfertanteRequest(
                    frequencia_festas = prefs.partyFrequency,
                    habitos_limpeza = prefs.cleaningHabit,
                    aceita_pets = prefs.acceptsPets,
                    horario_sono = prefs.sleepRoutine,
                    aceita_dividir_quarto = prefs.acceptsRoomSharing,
                )

                if (hasExisting) api.updateInteressesOfertante(userId, auth, body)
                else api.createInteressesOfertante(userId, auth, body)
            }

            if (!response.isSuccessful) {
                Result.failure(IllegalStateException("Erro ao salvar preferências: ${response.code()} ${response.message()}"))
            } else {
                Result.success(Unit)
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
