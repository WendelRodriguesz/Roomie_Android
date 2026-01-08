package com.roomie.app.core.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.navigation.AppNavHost
import com.roomie.app.navigation.Routes

@RoomiePreview
@Composable
fun Preview_AppNavHost_START_MATCH() {
    Roomie_AndroidTheme(dynamicColor = false) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            AppNavHost(startDestination = Routes.MATCH)
        }
    }
}
