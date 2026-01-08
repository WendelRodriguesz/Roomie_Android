package com.roomie.app.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.home.model.ApartamentosMock
import com.roomie.app.feature.home.presentation.HomeEvent
import com.roomie.app.feature.home.presentation.HomeState
import com.roomie.app.feature.home.ui.components.CardApartamento
import com.roomie.app.feature.home.ui.components.HomeSearchBar
import com.roomie.app.feature.match.model.ListingCard

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
    onListingClick: (ListingCard) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { HeaderSection() }
        item {
            HomeSearchBar(
                query = state.searchQuery,
                isFiltering = state.isFiltering,
                onQueryChange = { onEvent(HomeEvent.SearchQueryChanged(it)) },
                onFilterClick = { onEvent(HomeEvent.FiltersClicked) }
            )
        }
        if (state.hasResults) {
            items(state.listings, key = { it.id }) { listing ->
                CardApartamento(
                    anuncio = listing,
                    aoClicar = { onListingClick(listing) }
                )
            }
        } else {
            item { EmptyState(onReset = { onEvent(HomeEvent.Refresh) }) }
        }
        item { Spacer(Modifier.height(24.dp)) }
    }
}

@Composable
private fun HeaderSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text(
            text = "Apartamentos Disponíveis",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
        )
        Text(
            text = "Encontre o lar perfeito para dividir",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun EmptyState(onReset: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Nada encontrado",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Tente ajustar a busca ou filtros.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Button(onClick = onReset) {
                Text("Limpar")
            }
        }
    }
}

@RoomiePreview
@Composable
private fun HomeScreenPreview() {
    Roomie_AndroidTheme {
        val state = HomeState(listings = ApartamentosMock.itens)
        HomeScreen(
            state = state,
            onEvent = { _ -> },
            onListingClick = {}
        )
    }
}
