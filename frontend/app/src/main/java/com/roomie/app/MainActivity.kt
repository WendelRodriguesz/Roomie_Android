package com.roomie.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.roomie.app.navigation.AppNavHost
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        actionBar?.hide()
        installSplashScreen()
        setContent {
            Roomie_AndroidTheme {
                Surface {
                        AppNavHost()
                }
            }
        }
    }
}
