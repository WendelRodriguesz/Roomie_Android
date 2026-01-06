package com.roomie.app.feature.profile.data

import com.roomie.app.core.data.api.ProfileApiService
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.profile.model.UserProfile
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
}
