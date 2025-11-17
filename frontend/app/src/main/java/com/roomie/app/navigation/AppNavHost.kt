package com.roomie.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.roomie.app.core.ui.components.BottomBar
import com.roomie.app.feature.home.ui.HomeScreen
import com.roomie.app.feature.login.ui.LoginScreen
import com.roomie.app.feature.register.ui.RegisterScreen
import com.roomie.app.feature.welcome_screen.ui.WelcomeScreen

@Composable
fun AppNavHost(startDestination: String) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in Routes.BOTTOM_BAR_ROUTES) {
                BottomBar(currentRoute) { route ->
                    if (route != currentRoute) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    ) { inner ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(inner)
        ) {
            composable(Routes.HOME)   { HomeScreen() }

            composable(Routes.WELCOME_SCREEN) {
                WelcomeScreen(navController
                )
            }

            composable(Routes.LOGIN) {
                LoginScreen(navController)
            }

            composable(Routes.REGISTER) {
                RegisterScreen(navController)
            }
        }
    }
}
