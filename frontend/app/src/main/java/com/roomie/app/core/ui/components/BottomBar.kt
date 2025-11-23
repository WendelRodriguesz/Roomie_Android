package com.roomie.app.core.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.roomie.app.navigation.bottomDestinations
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.navigation.Routes

@Composable
fun BottomBar(selectedRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar (
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ){
        bottomDestinations.forEach { dest ->
            val isSelected = selectedRoute == dest.route
            val isMatch = dest.route == Routes.MATCH

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(dest.route) },
                icon = {
                    if (isMatch) {
                        Icon(
                            painter = rememberVectorPainter(dest.icon),
                            contentDescription = dest.label,
                            modifier = Modifier.size(45.dp) .padding(bottom = 5.dp),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    } else {
                        Icon(
                            painter = rememberVectorPainter(dest.icon),
                            contentDescription = dest.label,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
//                label = { Text(dest.label) }
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor    = MaterialTheme.colorScheme.primary,
                    selectedTextColor    = MaterialTheme.colorScheme.primary,
                    unselectedIconColor  = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor  = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor       = MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f)
                )
            )
        }
    }
}

@RoomiePreview
@Composable
fun Preview_BottomBar() {
    Roomie_AndroidTheme (dynamicColor = false){
        Surface(color = MaterialTheme.colorScheme.background){BottomBar(selectedRoute = Routes.MATCH) { } }}
}


