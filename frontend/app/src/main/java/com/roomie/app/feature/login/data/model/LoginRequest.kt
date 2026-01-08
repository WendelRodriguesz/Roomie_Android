package com.roomie.app.feature.login.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("senha")
    val senha: String
)

