package com.roomie.app.feature.profile.data.remote.dto

data class AtualizarUsuarioBasicoRequest(
    val nome: String,
    val email: String?,
    val cidade: String,
    val ocupacao: String?,
    val bio: String?,
    val genero: String?,
)
