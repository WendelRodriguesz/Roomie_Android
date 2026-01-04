package com.roomie.app.core.data.session

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.roomie.app.core.model.ProfileRole

object AuthSession {
    var userId: Long? by mutableStateOf(null)
    var token: String? by mutableStateOf(null)
    var refreshToken: String? by mutableStateOf(null)
    var role: ProfileRole? by mutableStateOf(null)

    val isLoggedIn: Boolean
        get() = !token.isNullOrBlank() && userId != null

    fun clear() {
        userId = null
        token = null
        refreshToken = null
        role = null
    }
}
