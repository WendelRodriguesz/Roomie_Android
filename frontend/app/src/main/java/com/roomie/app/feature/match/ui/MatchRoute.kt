package com.roomie.app.feature.match.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.roomie.app.feature.match.presentation.MatchEvent
import com.roomie.app.feature.match.presentation.MatchViewModel

@Composable
fun MatchRoute(vm: MatchViewModel = viewModel()) {
    val ui by vm.state.collectAsState()
    MatchScreen(state = ui, onEvent = vm::onEvent)
}
