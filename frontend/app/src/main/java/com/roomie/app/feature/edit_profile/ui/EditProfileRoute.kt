package com.roomie.app.feature.edit_profile.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.feature.edit_profile.presentation.EditProfileUiState
import com.roomie.app.feature.edit_profile.presentation.EditProfileViewModel
import com.roomie.app.feature.edit_profile.presentation.EditProfileViewModelFactory
import com.roomie.app.feature.profile.data.ProfileRepository

@Composable
fun EditProfileRoute(
    userId: Long,
    token: String,
    onCancel: () -> Unit,
    onSaved: () -> Unit,
) {
    val repository = ProfileRepository(RetrofitClient.profileApiService)
    val viewModel: EditProfileViewModel = viewModel(
        factory = EditProfileViewModelFactory(repository)
    )

    val uiState: EditProfileUiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadProfile(userId, token)
    }

    LaunchedEffect(uiState.saveSuccessful) {
        if (uiState.saveSuccessful) {
            onSaved()
            viewModel.consumeSaveSuccess()
        }
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage != null && uiState.profile == null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Snackbar {
                    Text(text = uiState.errorMessage)
                }
            }
        }

        uiState.profile != null -> {
            EditProfileScreen(
                profile = uiState.profile,
                isSaving = uiState.isSaving,
                onCancelClick = onCancel,
                onSaveClick = { updated ->
                    viewModel.saveProfile(userId, token, updated)
                }
            )
        }
    }
}
