package com.roomie.app.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.navigation.BottomDest
import com.roomie.app.navigation.Routes
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme

@Composable
fun BottomBar(
    selectedRoute: String?,
    destinations: List<BottomDest>,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        destinations.forEach { dest ->
            val isSelected = selectedRoute == dest.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(dest.route) },
                icon = {
                    Icon(
                        painter = rememberVectorPainter(dest.icon),
                        contentDescription = dest.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f)
                )
            )
        }
    }
}

@RoomiePreview
@Composable
fun Preview_BottomBar() {
    Roomie_AndroidTheme (dynamicColor = false){
        Surface(color = MaterialTheme.colorScheme.background){BottomBar(selectedRoute = Routes.MATCH, destinations = listOf(
            BottomDest(Routes.HOME, Icons.Outlined.Home,              "Home"),
            BottomDest(Routes.CHAT,          Icons.Outlined.ChatBubbleOutline, "Chat"),
            BottomDest(Routes.MATCH,         Icons.Outlined.Favorite,          "Match"),
            BottomDest(Routes.NOTIFICATIONS, Icons.Outlined.Notifications,     "Notifications"),
            BottomDest(Routes.PROFILE,       Icons.Outlined.Person,           "Profile"),
        ))  { } }
    }
}
