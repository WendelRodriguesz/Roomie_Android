package com.roomie.app.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.feature.home.presentation.HomeViewModel

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = viewModel(),
    onListingClick: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    HomeScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onListingClick = { onListingClick(it.id) }
    )
}

