package com.roomie.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.roomie.app.core.model.ProfileRole

data class BottomDest(
    val route: String,
    val icon: ImageVector,
    val label: String
)

fun bottomDestinationsFor(role: ProfileRole): List<BottomDest> {
    return when (role) {
        ProfileRole.SEEKER -> listOf(
            BottomDest(Routes.HOME,          Icons.Outlined.Home,              "Home"),
            BottomDest(Routes.CHAT,          Icons.Outlined.ChatBubbleOutline, "Chat"),
            BottomDest(Routes.MATCH,         Icons.Outlined.Favorite,          "Match"),
            BottomDest(Routes.NOTIFICATIONS, Icons.Outlined.Notifications,     "Notifications"),
            BottomDest(Routes.PROFILE,       Icons.Outlined.Person,           "Profile"),
        )

        ProfileRole.OFFEROR -> listOf(
            BottomDest(Routes.MY_LISTINGS,   Icons.Outlined.Home,              "Vagas"),
            BottomDest(Routes.CHAT,          Icons.Outlined.ChatBubbleOutline, "Chat"),
            BottomDest(Routes.MATCH,         Icons.Outlined.Favorite,          "Match"),
            BottomDest(Routes.NOTIFICATIONS, Icons.Outlined.Notifications,     "Notifications"),
            BottomDest(Routes.PROFILE,       Icons.Outlined.Person,           "Profile"),
        )
    }
}
