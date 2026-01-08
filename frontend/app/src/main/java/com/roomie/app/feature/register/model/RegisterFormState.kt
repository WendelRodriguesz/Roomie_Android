package com.roomie.app.feature.register.model

data class RegisterFormState(
    val nome: String = "",
    val email: String = "",
    val senha: String = "",
    val dataNascimento: String = "",
    val cidade: String = "",
    val genero: String = ""
)
