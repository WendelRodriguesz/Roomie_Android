package com.roomie.app.core.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.roomie.app.navigation.bottomDestinations

// Stateless: recebe rota atual e callback de navegação
@Composable
fun BottomBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar {
        bottomDestinations.forEach { dest ->
            NavigationBarItem(
                selected = currentRoute == dest.route,
                onClick = { onNavigate(dest.route) },
                icon = { Icon(dest.icon, contentDescription = dest.label) },
                label = { Text(dest.label) }
            )
        }
    }
}
