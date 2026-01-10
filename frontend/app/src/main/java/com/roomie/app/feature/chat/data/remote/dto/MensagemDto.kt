package com.roomie.app.feature.chat.data.remote.dto

data class MensagemDto(
    val id: Long,
    val id_chat: Long,
    val id_remetente: Long,
    val id_destinatario: Long,
    val conteudo: String,
    val enviada_em: String
)

