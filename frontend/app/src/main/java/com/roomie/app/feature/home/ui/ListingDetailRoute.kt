package com.roomie.app.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.feature.home.presentation.ListingDetailViewModel
import com.roomie.app.feature.home.presentation.ListingDetailViewModelFactory

@Composable
fun ListingDetailRoute(
    listingId: Int,
    onNavigateBack: () -> Unit,
    viewModel: ListingDetailViewModel = viewModel(
        factory = ListingDetailViewModelFactory(listingId)
    )
) {
    val state by viewModel.state.collectAsState()
    
    ListingDetailScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack
    )
}

