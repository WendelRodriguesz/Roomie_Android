package com.roomie.app.feature.match.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.feature.match.presentation.MatchViewModel

@Composable
fun MatchRoute(vm: MatchViewModel = viewModel()) {
    val ui by vm.state.collectAsState()
    MatchScreen(state = ui, onEvent = vm::onEvent)
}
