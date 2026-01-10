package com.roomie.app.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.feature.home.presentation.HomeViewModel

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = viewModel(),
    onListingClick: (Int) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    HomeScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onListingClick = { listing ->
            val listingId = listing.id.toIntOrNull() ?: 0
            onListingClick(listingId)
        }
    )
}

