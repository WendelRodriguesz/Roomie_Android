package com.roomie.app.feature.vaga.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.feature.vaga.presentation.CreateListingEvent
import com.roomie.app.feature.vaga.presentation.CreateListingViewModel

@Composable
fun CreateListingRoute(
    viewModel: CreateListingViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    
    CreateListingScreen(
        state = state,
        onEvent = { event ->
            viewModel.onEvent(event)
            if (event is CreateListingEvent.Submit && state.isValid) {
                // Para implementar ao integrar com o backend
            }
        },
        onCancel = onNavigateBack
    )
}

