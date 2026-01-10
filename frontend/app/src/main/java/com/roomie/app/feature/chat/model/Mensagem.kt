package com.roomie.app.feature.chat.model

data class Mensagem(
    val id: Long,
    val idChat: Long,
    val idRemetente: Long,
    val idDestinatario: Long,
    val conteudo: String,
    var enviadaEm: String,
    val isMine: Boolean
)