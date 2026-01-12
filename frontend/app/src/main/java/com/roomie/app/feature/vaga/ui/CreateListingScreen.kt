package com.roomie.app.feature.vaga.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.components.LabeledOutlinedField
import com.roomie.app.core.ui.components.RoomieSelect
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.preference_registration.components.SectionCard
import com.roomie.app.feature.vaga.model.TipoComodo
import com.roomie.app.feature.vaga.model.TipoImovel
import com.roomie.app.feature.vaga.presentation.CreateListingEvent
import com.roomie.app.feature.vaga.presentation.CreateListingState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateListingScreen(
    state: CreateListingState,
    onEvent: (CreateListingEvent) -> Unit,
    onCancel: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.validationError) {
        state.validationError?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
            onEvent(CreateListingEvent.ClearValidationError)
        }
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
            onEvent(CreateListingEvent.ClearError)
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Cadastrar Vaga") },
                actions = {
                    TextButton(onClick = onCancel) {
                        Text("Cancelar")
                    }
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        enabled = !state.isLoading,
                        onClick = { onEvent(CreateListingEvent.Submit) }
                    ) {
                        Text(if (state.isLoading) "Salvando..." else "Cadastrar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            SectionCard(title = "Informações básicas") {
                LabeledOutlinedField(
                    title = "Título da vaga",
                    value = state.formData.titulo,
                    onValueChange = { onEvent(CreateListingEvent.TituloChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    supportingText = {
                        Text(
                            text = "${state.formData.titulo.length}/100",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                )

                LabeledOutlinedField(
                    title = "Descrição",
                    value = state.formData.descricao,
                    onValueChange = { onEvent(CreateListingEvent.DescricaoChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    minLines = 3,
                    maxLines = 6,
                    supportingText = {
                        Text(
                            text = "${state.formData.descricao.length}/500",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                )
            }

            SectionCard(title = "Endereço") {
                LabeledOutlinedField(
                    title = "Rua",
                    value = state.formData.rua,
                    onValueChange = { onEvent(CreateListingEvent.RuaChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LabeledOutlinedField(
                        title = "Número",
                        value = state.formData.numero,
                        onValueChange = { onEvent(CreateListingEvent.NumeroChanged(it)) },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    LabeledOutlinedField(
                        title = "Bairro",
                        value = state.formData.bairro,
                        onValueChange = { onEvent(CreateListingEvent.BairroChanged(it)) },
                        modifier = Modifier.weight(2f),
                        singleLine = true
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LabeledOutlinedField(
                        title = "Cidade",
                        value = state.formData.cidade,
                        onValueChange = { onEvent(CreateListingEvent.CidadeChanged(it)) },
                        modifier = Modifier.weight(2f),
                        singleLine = true
                    )

                    LabeledOutlinedField(
                        title = "Estado",
                        value = state.formData.estado,
                        onValueChange = { onEvent(CreateListingEvent.EstadoChanged(it)) },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }

            SectionCard(title = "Valores") {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LabeledOutlinedField(
                        title = "Valor do aluguel (R$)",
                        value = state.formData.valorAluguel?.toString() ?: "",
                        onValueChange = { onEvent(CreateListingEvent.ValorAluguelChanged(it)) },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    LabeledOutlinedField(
                        title = "Valor das contas (R$)",
                        value = state.formData.valorContas?.toString() ?: "",
                        onValueChange = { onEvent(CreateListingEvent.ValorContasChanged(it)) },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Column {
                    Text(
                        text = "Vagas disponíveis: ${state.formData.vagasDisponiveis}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(4.dp))
                    Slider(
                        value = state.formData.vagasDisponiveis.toFloat(),
                        onValueChange = { onEvent(CreateListingEvent.VagasDisponiveisChanged(it.toInt())) },
                        valueRange = 1f..10f,
                        steps = 8
                    )
                }
            }

            SectionCard(title = "Detalhes do imóvel") {
                RoomieSelect(
                    value = state.formData.tipoImovel,
                    onValueChange = { onEvent(CreateListingEvent.TipoImovelChanged(it)) },
                    items = TipoImovel.entries,
                    itemLabel = { it.label },
                    placeholder = "Selecione o tipo de imóvel",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Cômodos disponíveis",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                TipoComodo.entries.forEach { comodo ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val newComodos = if (state.formData.comodos.contains(comodo)) {
                                    state.formData.comodos - comodo
                                } else {
                                    state.formData.comodos + comodo
                                }
                                onEvent(CreateListingEvent.ComodosChanged(newComodos))
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Checkbox(
                            checked = state.formData.comodos.contains(comodo),
                            onCheckedChange = {
                                val newComodos = if (it) {
                                    state.formData.comodos + comodo
                                } else {
                                    state.formData.comodos - comodo
                                }
                                onEvent(CreateListingEvent.ComodosChanged(newComodos))
                            }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = comodo.label,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@RoomiePreview
@Composable
private fun CreateListingScreenPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        CreateListingScreen(
            state = CreateListingState(),
            onEvent = {},
            onCancel = {}
        )
    }
}
