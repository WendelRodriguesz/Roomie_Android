package com.roomie.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomDest(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val bottomDestinations = listOf(
    BottomDest(Routes.HOME,  Icons.Filled.Home,          "Home"),
    BottomDest(Routes.CHAT,  Icons.Filled.Chat,          "Conversas"),
    BottomDest(Routes.LIKES, Icons.Filled.Favorite,      "Likes"),
    BottomDest(Routes.NOTIFS,Icons.Filled.Notifications, "Notificações"),
    BottomDest(Routes.ACCOUNT, Icons.Filled.Person,      "Conta")
)
