package com.roomie.app.feature.profile.data

import com.roomie.app.core.data.api.ProfileApiService
import com.roomie.app.feature.profile.model.UserProfile
import com.roomie.app.feature.profile.model.toUserProfile
import com.roomie.app.feature.profile.model.toUpdateRequest

class ProfileRepository(
    private val api: ProfileApiService,
) {

    suspend fun getUserProfile(
        userId: Long,
        token: String,
    ): Result<UserProfile> {
        return try {
            val response = api.getUsuarioInteressado(
                id = userId,
                authHeader = "Bearer $token"
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toUserProfile())
                } else {
                    Result.failure(IllegalStateException("Resposta da API vazia"))
                }
            } else {
                Result.failure(
                    IllegalStateException("Erro da API: ${response.code()} ${response.message()}")
                )
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    suspend fun updateUserProfile(
        userId: Long,
        token: String,
        profile: UserProfile,
    ): Result<UserProfile> {
        return try {
            val request = profile.toUpdateRequest()
            val response = api.updateUsuarioInteressado(
                id = userId,
                authHeader = "Bearer $token",
                body = request
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) Result.success(body.toUserProfile())
                else Result.failure(IllegalStateException("Resposta da API vazia na atualização"))
            } else {
                Result.failure(
                    IllegalStateException("Erro da API na atualização: ${response.code()} ${response.message()}")
                )
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}