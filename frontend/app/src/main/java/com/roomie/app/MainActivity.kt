package com.roomie.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.roomie.app.core.data.firebase.NotificationHelper
import com.roomie.app.core.ui.theme.AppSettings
import com.roomie.app.core.ui.theme.LocalAppSettings
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.navigation.AppNavHost
import com.roomie.app.navigation.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        actionBar?.hide()
        installSplashScreen()
        
        // Criar canal de notificações ao iniciar o app
        NotificationHelper.createNotificationChannel(this)

        setContent {
            val appSettingsState = remember {
                mutableStateOf(AppSettings())
            }

            CompositionLocalProvider(LocalAppSettings provides appSettingsState) {
                Roomie_AndroidTheme(
                    dynamicColor = false,
                    darkTheme = appSettingsState.value.darkModeEnabled
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavHost(Routes.WELCOME_SCREEN)
                    }
                }
            }
        }
    }
}