package com.roomie.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import com.roomie.app.R

data class BottomDest(
    val route: String,
    val icon: ImageVector,
//    @DrawableRes val iconRes: ImageVector,
    val label: String
)

val bottomDestinations = listOf(
    BottomDest(Routes.HOME,          Icons.Outlined.Home,         "Home"),
    BottomDest(Routes.CHAT,          Icons.Outlined.ChatBubbleOutline,         "Chat"),
    BottomDest(Routes.MATCH,         Icons.Outlined.Favorite,     "Match"),
    BottomDest(Routes.NOTIFICATIONS, Icons.Outlined.Notifications,"Notifications"),
    BottomDest(Routes.PROFILE,       Icons.Outlined.Person,      "Profile")
)

