package com.roomie.app.core.data.api

import com.roomie.app.feature.register.data.remote.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST("api/usuarioOfertante/cadastrar")
    suspend fun registerOfferor(
        @Body request: RegisterRequest
    ): Response<Unit>

    @POST("api/usuarioInteressado/cadastrar")
    suspend fun registerSeeker(
        @Body request: RegisterRequest
    ): Response<Unit>
}