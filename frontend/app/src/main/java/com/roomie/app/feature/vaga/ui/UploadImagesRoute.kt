package com.roomie.app.feature.vaga.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.feature.vaga.presentation.UploadImagesEvent
import com.roomie.app.feature.vaga.presentation.UploadImagesViewModel

@Composable
fun UploadImagesRoute(
    anuncioId: Long,
    viewModel: UploadImagesViewModel = viewModel(),
    onSkip: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    
    viewModel.setContext(context)
    viewModel.setAnuncioId(anuncioId)
    
    UploadImagesScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onSkip = onSkip,
        onComplete = onNavigateToHome
    )
}