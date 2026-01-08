package com.roomie.app.feature.edit_profile.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.edit_profile.presentation.EditPreferencesViewModel
import com.roomie.app.feature.edit_profile.presentation.EditPreferencesViewModelFactory
import com.roomie.app.feature.preference_registration.ui.PreferenceRegistration
import com.roomie.app.feature.profile.data.ProfileRepository

@Composable
fun EditPreferencesRoute(
    userId: Long,
    token: String,
    role: ProfileRole,
    onCancel: () -> Unit,
    onSaved: () -> Unit,
) {
    val repository = remember { ProfileRepository(RetrofitClient.profileApiService) }
    val api = remember { RetrofitClient.profileApiService }
    val viewModel: EditPreferencesViewModel = viewModel(
        factory = EditPreferencesViewModelFactory(repository, api)
    )
    val uiState = viewModel.uiState

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(userId, token, role) {
        viewModel.loadPreferences(role = role, userId = userId, token = token)
    }

    LaunchedEffect(uiState.saveSuccessful) {
        if (uiState.saveSuccessful) {
            onSaved()
            viewModel.consumeSaveSuccess()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        val msg = uiState.errorMessage
        if (!msg.isNullOrBlank()) snackbarHostState.showSnackbar(msg)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { _ ->
        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.preferences != null -> {
                PreferenceRegistration(
                    role = role,
                    initialPreferences = uiState.preferences,
                    isSaving = uiState.isSaving,
                    onSaveClick = { prefs ->
                        viewModel.savePreferences(role, token, prefs)
                    },
                    onSkipClick = onCancel,
                    skipText = "Cancelar"
                )
            }

            else -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
