package com.roomie.app.feature.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.components.LabeledOutlinedField
import com.roomie.app.core.ui.components.RoomieSelect
import com.roomie.app.feature.home.presentation.HomeEvent
import com.roomie.app.feature.home.presentation.HomeState
import com.roomie.app.feature.preference_registration.components.SectionCard
import com.roomie.app.feature.vaga.model.TipoImovel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Filtros",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent(HomeEvent.CloseFilters) }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Fechar filtros"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SectionCard(title = "Localização") {
                LabeledOutlinedField(
                    title = "Cidade",
                    value = state.filterCidade,
                    onValueChange = { onEvent(HomeEvent.FilterCidadeChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                LabeledOutlinedField(
                    title = "Bairro",
                    value = state.filterBairro,
                    onValueChange = { onEvent(HomeEvent.FilterBairroChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
                }

                SectionCard(title = "Custo") {
                LabeledOutlinedField(
                    title = "Valor mínimo",
                    value = state.filterCustoMin,
                    onValueChange = { onEvent(HomeEvent.FilterCustoMinChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                LabeledOutlinedField(
                    title = "Valor máximo",
                    value = state.filterCustoMax,
                    onValueChange = { onEvent(HomeEvent.FilterCustoMaxChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
                }

                SectionCard(title = "Características") {
                RoomieSelect(
                    value = state.filterTipoImovel?.let { 
                        TipoImovel.values().find { tipo -> tipo.apiValue == it }
                    },
                    onValueChange = { 
                        onEvent(HomeEvent.FilterTipoImovelChanged(it.apiValue))
                    },
                    items = TipoImovel.values().toList(),
                    itemLabel = { it.label },
                    placeholder = "Selecione o tipo de imóvel",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                LabeledOutlinedField(
                    title = "Vagas mínimas",
                    value = state.filterVagasMin,
                    onValueChange = { onEvent(HomeEvent.FilterVagasMinChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
                }

                SectionCard(title = "Preferências") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .toggleable(
                            value = state.filterAceitaPets == true,
                            onValueChange = { 
                                onEvent(HomeEvent.FilterAceitaPetsChanged(if (it) true else null))
                            },
                            role = Role.Checkbox
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = state.filterAceitaPets == true,
                        onCheckedChange = { 
                            onEvent(HomeEvent.FilterAceitaPetsChanged(if (it) true else null))
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Aceita pets",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .toggleable(
                            value = state.filterAceitaDividirQuarto == true,
                            onValueChange = { 
                                onEvent(HomeEvent.FilterAceitaDividirQuartoChanged(if (it) true else null))
                            },
                            role = Role.Checkbox
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = state.filterAceitaDividirQuarto == true,
                        onCheckedChange = { 
                            onEvent(HomeEvent.FilterAceitaDividirQuartoChanged(if (it) true else null))
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Aceita dividir quarto",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                }

                Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { onEvent(HomeEvent.ClearFilters) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Limpar")
                }
                Button(
                    onClick = { onEvent(HomeEvent.ApplyFilters) },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isLoading
                ) {
                    Text(if (state.isLoading) "Filtrando..." else "Aplicar")
                }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

