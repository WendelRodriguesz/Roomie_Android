package com.roomie.app.feature.register.data.remote.dto

data class RegisterRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val data_de_nascimento: String,
    val cidade: String,
    val genero: String,
    val ocupacao: String = " ",
    val bio: String = " "
)