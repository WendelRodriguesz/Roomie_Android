package com.roomie.app.feature.chat.model

data class ChatListItem(
    val chatId: Long,
    val otherUserId: Long,
    val otherUserName: String,
    val otherUserPhotoUrl: String?,
    val otherUserAge: Int?,
    val otherUserCity: String,
    val otherUserOccupation: String?,
    val otherUserBio: String?,
    val usadoEm: String,
    val isOfertante: Boolean, // true se o usuário logado é ofertante, false se é interessado
    val anuncioTitulo: String? = null // null se for interessado, título do anúncio se for ofertante
)

