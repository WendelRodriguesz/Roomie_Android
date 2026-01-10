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
    val isOfertante: Boolean,
    val anuncioTitulo: String? = null
)