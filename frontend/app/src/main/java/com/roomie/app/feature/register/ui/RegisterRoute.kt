package com.roomie.app.feature.register.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.roomie.app.navigation.Routes

@Composable
fun RegisterRoute(navController: NavController) {
    val backStackEntry = navController.getBackStackEntry(Routes.REGISTER)
    val viewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(),
        viewModelStoreOwner = backStackEntry
    )

    RegisterScreen(navController = navController, viewModel = viewModel)
}

@Composable
fun RegisterRoleScreen(navController: NavController) {
    val backStackEntry = navController.getBackStackEntry(Routes.REGISTER)
    val viewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(),
        viewModelStoreOwner = backStackEntry
    )

    RegisterRoleScreen(navController = navController, viewModel = viewModel)
}

