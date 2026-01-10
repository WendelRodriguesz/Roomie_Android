package com.roomie.app.feature.edit_profile.ui

import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.edit_profile.presentation.EditProfileViewModel
import com.roomie.app.feature.edit_profile.presentation.EditProfileViewModelFactory
import com.roomie.app.feature.profile.data.ProfileRepository
import kotlinx.coroutines.launch

@Composable
fun EditProfileRoute(
    userId: Long,
    token: String,
    role: ProfileRole,
    navController: NavController,
    onCancel: () -> Unit,
    onSaved: () -> Unit,
) {
    val repository = remember { ProfileRepository(RetrofitClient.profileApiService) }
    val viewModel: EditProfileViewModel = viewModel(factory = EditProfileViewModelFactory(repository))
    val uiState = viewModel.uiState

    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(userId, token, role) {
        viewModel.loadProfile(role = role, userId = userId, token = token)
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

    fun queryFileName(uri: Uri): String {
        val cr = ctx.contentResolver
        val cursor: Cursor? = cr.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
        if (cursor != null) {
            cursor.use {
                val idx = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (it.moveToFirst() && idx >= 0) {
                    val value = it.getString(idx)
                    if (!value.isNullOrBlank()) return value
                }
            }
        }
        return "profile.jpg"
    }

    val pickPhoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult

        scope.launch {
            val bytes = ctx.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            if (bytes == null || bytes.isEmpty()) {
                snackbarHostState.showSnackbar("Não consegui ler a imagem.")
                return@launch
            }

            val mime = ctx.contentResolver.getType(uri)
            val fileName = queryFileName(uri)

            viewModel.uploadPhoto(
                role = role,
                userId = userId,
                token = token,
                bytes = bytes,
                fileName = fileName,
                mimeType = mime,
            )
        }
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

            uiState.profile != null -> {
                EditProfileScreen(
                    profile = uiState.profile,
                    isSaving = uiState.isSaving,
                    isUploadingPhoto = uiState.isUploadingPhoto,
                    onCancelClick = onCancel,
                    onSaveClick = { updated -> viewModel.saveProfile(role, userId, token, updated) },
                    onPhotoChangeClick = {
                        pickPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
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
