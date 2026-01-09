package com.roomie.app.feature.offeror_home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.feature.offeror_home.data.AnuncioRepository
import com.roomie.app.feature.offeror_home.presentation.OfferorHomeEvent
import com.roomie.app.feature.offeror_home.presentation.OfferorHomeViewModel
import com.roomie.app.feature.offeror_home.presentation.OfferorHomeViewModelFactory

@Composable
fun OfferorHomeRoute(
    anuncioId: Long,
    token: String,
    refreshSignal: Long = 0L,
    onEditClick: () -> Unit = {},
    onError: (String) -> Unit = {}
) {
    val repository = remember { AnuncioRepository(RetrofitClient.anuncioApiService) }
    
    val viewModel: OfferorHomeViewModel = viewModel(
        factory = OfferorHomeViewModelFactory(
            repository = repository,
            anuncioId = anuncioId,
            token = token
        )
    )

    val state by viewModel.state.collectAsState()

    LaunchedEffect(refreshSignal) {
        if (refreshSignal > 0) {
            viewModel.onEvent(OfferorHomeEvent.LoadAnuncio)
        }
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { error ->
            onError(error)
        }
    }

    OfferorHomeScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onEditClick = onEditClick
    )
}
