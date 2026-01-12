package com.roomie.app.feature.vaga.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.feature.vaga.presentation.CreateListingEvent
import com.roomie.app.feature.vaga.presentation.CreateListingViewModel

@Composable
fun CreateListingRoute(
    viewModel: CreateListingViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToUploadImages: (Long) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    
    CreateListingScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onCancel = onNavigateBack
    )
    
    LaunchedEffect(state.isSubmitted, state.createdAnuncioId) {
        if (state.isSubmitted && state.createdAnuncioId != null) {
            onNavigateToUploadImages(state.createdAnuncioId!!)
        }
    }
}

