package com.roomie.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.roomie.app.core.ui.components.BottomBar
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.home.ui.HomeRoute
import com.roomie.app.feature.login.ui.LoginScreen
import com.roomie.app.feature.profile.ui.ProfileScreen
import com.roomie.app.feature.chat.ui.ChatScreen
import com.roomie.app.feature.edit_profile.ui.EditProfileScreen
import com.roomie.app.feature.match.model.MatchMock
import com.roomie.app.feature.match.presentation.MatchState
import com.roomie.app.feature.match.ui.MatchRoute
import com.roomie.app.feature.match.ui.MatchScreen
import com.roomie.app.feature.notifications.ui.NotificationsScreen
import com.roomie.app.feature.preference_registration.ui.PreferenceRegistration
import com.roomie.app.feature.profile.model.UserMock
import com.roomie.app.feature.register.ui.RegisterScreen
import com.roomie.app.feature.welcome_screen.ui.WelcomeScreen
import com.roomie.app.feature.vaga.ui.CadastrarVagasScreen
import com.roomie.app.feature.register.ui.RegisterRoleScreen
import com.roomie.app.feature.profile.ui.ProfileScreenRoute
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.feature.edit_profile.ui.EditProfileRoute

@Composable
fun AppNavHost(startDestination: String) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val selectedRoute = currentRoute ?: startDestination
    val showBottomBar = selectedRoute in Routes.BOTTOM_BAR_ROUTES

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    selectedRoute = selectedRoute,
                    onNavigate = { route ->
                        if (route != currentRoute) {
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { inner ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(inner)
        ) {
            composable(Routes.HOME)   { HomeRoute() }
            composable(Routes.CHAT)   { ChatScreen() }
            composable(Routes.MATCH) { MatchRoute() }
            composable(Routes.NOTIFICATIONS)   { NotificationsScreen() }
            composable(Routes.PROFILE) { backStackEntry ->
                val userId = AuthSession.userId
                val token = AuthSession.token

                if (userId == null || token.isNullOrBlank()) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                } else {
                    ProfileScreenRoute(
                        userId = userId,
                        token = token,
                        onEditClick = { navController.navigate(Routes.EDIT_PROFILE) },
                        onLogoutClick = {
                            AuthSession.clear()
                            navController.navigate(Routes.LOGIN) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }


            composable(Routes.WELCOME_SCREEN) { WelcomeScreen(navController) }
            composable(Routes.LOGIN) { LoginScreen(navController) }
            composable(Routes.REGISTER) { RegisterScreen(navController) }
            composable(Routes.REGISTER_ROLE) { RegisterRoleScreen(navController) }
            composable(Routes.EDIT_PROFILE) {
                val userId = AuthSession.userId
                val token = AuthSession.token

                if (userId == null || token.isNullOrBlank()) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                } else {
                    EditProfileRoute(
                        userId = userId,
                        token = token,
                        onCancel = { navController.popBackStack() },
                        onSaved = {
                            navController.navigate(Routes.PROFILE) {
                                popUpTo(Routes.PROFILE) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
            composable(Routes.ADD_VAGA) { CadastrarVagasScreen(navController)}
            composable(Routes.PREFERENCES_REGISTRATION) { PreferenceRegistration() }
        }
    }
}

@RoomiePreview
@Composable
private fun AppNavHostPreview(){
    Roomie_AndroidTheme(dynamicColor = false) {
        Surface (){
            AppNavHost(Routes.MATCH)
        }
    }
}