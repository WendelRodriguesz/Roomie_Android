package com.roomie.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.roomie.app.navigation.AppNavHost
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
            Roomie_AndroidTheme {
                Surface {
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
