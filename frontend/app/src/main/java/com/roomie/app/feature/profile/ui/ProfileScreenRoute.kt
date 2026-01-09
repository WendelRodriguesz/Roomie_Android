package com.roomie.app.feature.profile.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.profile.data.ProfileRepository

@Composable
fun ProfileScreenRoute(
    userId: Long,
    token: String,
    role: ProfileRole,
    refreshSignal: Long = 0L,
    onEditClick: () -> Unit = {},
    onEditPreferencesClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {
    val repository = remember { ProfileRepository(RetrofitClient.profileApiService) }

    val vm: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(repository)
    )

    val uiState = vm.uiState

    LaunchedEffect(userId, token, role, refreshSignal) {
        vm.loadProfile(role = role, userId = userId, token = token, forceReload = true)
    }

    when {
        uiState.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage != null -> {
            val message = uiState.errorMessage
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Snackbar { Text(text = message ?: "Erro inesperado") }
            }
        }

        uiState.profile != null -> {
            ProfileScreen(
                profile = uiState.profile,
                onEditClick = onEditClick,
                onEditPreferencesClick = onEditPreferencesClick,
                onLogoutClick = onLogoutClick,
            )
        }
    }
}
