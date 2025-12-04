package com.roomie.app.feature.login.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("token")
    val token: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("role")
    val role: String
)

