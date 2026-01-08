package com.roomie.app.feature.register.data

import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.register.data.remote.dto.RegisterRequest

class RegisterRepository {
    private val api = RetrofitClient.registerApiService

    suspend fun createUser(
        role: ProfileRole,
        request: RegisterRequest,
    ): Result<Unit> {
        return try {
            val response = when (role) {
                ProfileRole.OFFEROR -> api.registerOfferor(request)
                ProfileRole.SEEKER -> api.registerSeeker(request)
            }

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorMessage =
                    response.errorBody()?.string() ?: "Erro ao cadastrar usuário. Tente novamente."
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
