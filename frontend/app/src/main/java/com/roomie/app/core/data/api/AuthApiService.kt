package com.roomie.app.core.data.api

import com.roomie.app.feature.login.data.model.LoginRequest
import com.roomie.app.feature.login.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/auth/logar")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}

