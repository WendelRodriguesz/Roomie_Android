package com.roomie.app.core.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // Ambiente em nuvem (produção já configurada)
    private const val BASE_URL = "https://roomie-wi19.onrender.com/"

    // Se quiser testar local, troque para 10.0.2.2 (emulador) ou IP da máquina:
    // private const val BASE_URL = "http://10.0.2.2:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApiService: AuthApiService = retrofit.create(AuthApiService::class.java)
    val profileApiService: ProfileApiService = retrofit.create(ProfileApiService::class.java)
    val registerApiService: RegisterApiService = retrofit.create(RegisterApiService::class.java)
    val matchApiService: MatchApiService = retrofit.create(MatchApiService::class.java)
    val firebaseTokenApiService: FirebaseTokenApiService = retrofit.create(FirebaseTokenApiService::class.java)
}
