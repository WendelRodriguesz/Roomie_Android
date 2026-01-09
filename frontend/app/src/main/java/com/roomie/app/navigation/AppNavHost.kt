package com.roomie.app.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.core.ui.components.BottomBar
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.chat.ui.ChatScreen
import com.roomie.app.feature.edit_profile.ui.EditProfileRoute
import com.roomie.app.feature.home.ui.HomeRoute
import com.roomie.app.feature.login.ui.LoginScreen
import com.roomie.app.feature.match.ui.MatchRoute
import com.roomie.app.feature.notifications.ui.NotificationsScreen
import com.roomie.app.feature.preference_registration.ui.PreferenceRegistration
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.profile.ui.ProfileScreenRoute
import com.roomie.app.feature.register.ui.RegisterRoute
import com.roomie.app.feature.register.ui.RegisterRoleScreen
import com.roomie.app.feature.vaga.ui.CadastrarVagasScreen
import com.roomie.app.feature.vaga.ui.MyListingsScreen
import com.roomie.app.feature.welcome_screen.ui.WelcomeScreen
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.roomie.app.core.data.local.AuthDataStore
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(startDestination: String) {
    val navController = rememberNavController()

    val context = LocalContext.current
    val authDataStore = remember { AuthDataStore(context) }
    val scope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    var profileRefreshSignal by remember { mutableStateOf(0L) }
    val role = AuthSession.role

    val selectedRoute = run {
        if (currentRoute != null) currentRoute else startDestination
    }

    val destinations = run {
        if (role == null) emptyList() else bottomDestinationsFor(role)
    }

    val showBottomBar = run {
        if (role == null) {
            false
        } else {
            val allowedRoutes = destinations.map { it.route }
            allowedRoutes.contains(selectedRoute)
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    selectedRoute = selectedRoute,
                    destinations = destinations,
                    onNavigate = { route ->
                        if (route == currentRoute) {
                            if (route == Routes.PROFILE) {
                                profileRefreshSignal = System.currentTimeMillis()
                            }
                            return@BottomBar
                        }

                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
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
            composable(Routes.HOME) { HomeRoute() }
            composable(Routes.CHAT) { ChatScreen() }
            composable(Routes.MATCH) { MatchRoute() }
            composable(Routes.NOTIFICATIONS) { NotificationsScreen() }

            composable(Routes.PROFILE) {
                val userId = AuthSession.userId
                val token = AuthSession.token
                val currentRole = AuthSession.role

                if (userId == null || token.isNullOrBlank() || currentRole == null) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                        launchSingleTop = true
                    }
                    return@composable
                }

                ProfileScreenRoute(
                    userId = userId,
                    token = token,
                    role = currentRole,
                    refreshSignal = profileRefreshSignal,
                    onEditClick = { navController.navigate(Routes.EDIT_PROFILE) },
                    onLogoutClick = {
                        scope.launch {
                            authDataStore.clearUserSession()
                            AuthSession.clear()
                            navController.navigate(Routes.LOGIN) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                )
         }
            composable(Routes.WELCOME_SCREEN) { WelcomeScreen(navController) }
            composable(Routes.LOGIN) { LoginScreen(navController) }
            composable(Routes.REGISTER) { RegisterRoute(navController) }
            composable(Routes.REGISTER_ROLE) { RegisterRoleScreen(navController) }

            composable(Routes.EDIT_PROFILE) {
                val userId = AuthSession.userId
                val token = AuthSession.token
                val currentRole = AuthSession.role

                if (userId == null || token.isNullOrBlank() || currentRole == null) {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                        launchSingleTop = true
                    }
                } else {
                    EditProfileRoute(
                        userId = userId,
                        token = token,
                        role = currentRole,
                        onCancel = { navController.popBackStack() },
                        onSaved = {
                            profileRefreshSignal = System.currentTimeMillis()
                            navController.popBackStack()
                        }
                    )
                }
            }

            composable(Routes.ADD_VAGA) { CadastrarVagasScreen(navController) }

            composable(
                route = Routes.EDIT_ANUNCIO,
                arguments = listOf(
                    navArgument("anuncioId") { type = NavType.LongType }
                )
            ) { backStackEntry ->
                val anuncioId = backStackEntry.arguments?.getLong("anuncioId")
                val token = AuthSession.token

                if (anuncioId == null || token.isNullOrBlank()) {
                    navController.popBackStack()
                } else {
                    com.roomie.app.feature.offeror_home.ui.EditAnuncioRoute(
                        anuncioId = anuncioId,
                        token = token,
                        onCancel = { navController.popBackStack() },
                        onSaved = { navController.popBackStack() }
                    )
                }
            }

            composable(Routes.MY_LISTINGS) {
                MyListingsScreen(
                    navController = navController,
                    onCreateListingClick = { navController.navigate(Routes.ADD_VAGA) }
                )
            }

            composable(Routes.PREFERENCES_REGISTRATION) { PreferenceRegistration() }
        }
    }
}

@RoomiePreview
@Composable
private fun AppNavHostPreview() {
    AuthSession.userId = 1L
    AuthSession.token = "preview-token"
    AuthSession.role = ProfileRole.SEEKER

    Roomie_AndroidTheme(dynamicColor = false) {
        Surface {
            AppNavHost(Routes.MATCH)
        }
    }
}
