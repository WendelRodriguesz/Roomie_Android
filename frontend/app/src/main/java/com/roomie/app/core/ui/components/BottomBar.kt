package com.roomie.app.core.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.roomie.app.navigation.bottomDestinations
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Stateless: recebe rota atual e callback de navegação
@Composable
fun BottomBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar {
        bottomDestinations.forEach { dest ->
            NavigationBarItem(
                selected = currentRoute == dest.route,
                onClick = { onNavigate(dest.route) },
                icon = {
                    Icon(
                        //dest.icon,
                        painter = painterResource(dest.iconRes),
                        contentDescription = dest.label,
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    ) },
                label = { Text(dest.label) }
            )
        }
    }
}
