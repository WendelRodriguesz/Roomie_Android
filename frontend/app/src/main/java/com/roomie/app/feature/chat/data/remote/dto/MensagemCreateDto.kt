package com.roomie.app.feature.chat.data.remote.dto

data class MensagemCreateDto(
    val id_chat: Int,
    val id_remetente: Int,
    val id_destinatario: Int,
    val conteudo: String
)

