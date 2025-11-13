package com.roomie.app

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import com.roomie.app.navigation.AppNavHost
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.roomie_android.ui.theme.Roomie_AndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        actionBar?.hide()
        installSplashScreen()
        setContent {
            Roomie_AndroidTheme {
                Surface {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        AppNavHost()
                    }
                }
            }
        }
    }
}
