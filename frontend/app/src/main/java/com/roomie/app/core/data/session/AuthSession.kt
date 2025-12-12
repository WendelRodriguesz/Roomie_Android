package com.roomie.app.core.data.session

object AuthSession {
    var userId: Long? = null
    var token: String? = null
    var refreshToken: String? = null
    var role: String? = null

    val isLoggedIn: Boolean
        get() = !token.isNullOrBlank() && userId != null

    fun clear() {
        userId = null
        token = null
        refreshToken = null
        role = null
    }
}
