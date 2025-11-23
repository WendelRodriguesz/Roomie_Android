package com.roomie.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.roomie.app.navigation.AppNavHost
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.welcome_screen.ui.WelcomeScreen
import com.roomie.app.navigation.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        actionBar?.hide()
        installSplashScreen()
        setContent {
            Roomie_AndroidTheme (dynamicColor = false,darkTheme = true){
                Surface (Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
                    var loggedIn : Boolean = false // simulando

                    val startDestination = if (loggedIn) {
                        Routes.HOME
                    } else {
                        Routes.WELCOME_SCREEN
                    }

                    AppNavHost(startDestination)
                }
            }
        }
    }
}

