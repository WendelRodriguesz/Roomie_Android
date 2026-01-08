package com.roomie.app.core.data.model

data class UserSession(
    val id: Long,
    val token: String,
    val refreshToken: String,
    val role: String
)