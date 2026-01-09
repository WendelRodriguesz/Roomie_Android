package com.roomie.app.feature.offeror_home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roomie.app.core.ui.components.GradientButton
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.offeror_home.model.Anuncio
import com.roomie.app.feature.offeror_home.model.StatusAnuncio
import com.roomie.app.feature.offeror_home.presentation.OfferorHomeEvent
import com.roomie.app.feature.offeror_home.presentation.OfferorHomeState
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun OfferorHomeScreen(
    state: OfferorHomeState,
    onEvent: (OfferorHomeEvent) -> Unit,
    onEditClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.successMessage) {
        state.successMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            onEvent(OfferorHomeEvent.DismissSuccess)
        }
    }

    LaunchedEffect(state.errorMessage, state.anuncio) {
        state.errorMessage?.let { message ->
            if (state.anuncio != null) {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                onEvent(OfferorHomeEvent.DismissError)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Minha Vaga",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        when {
            state.isLoading && state.anuncio == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            state.anuncio != null -> {
                AnuncioContent(
                    anuncio = state.anuncio,
                    isLoading = state.isLoading,
                    onEvent = onEvent,
                    onEditClick = onEditClick,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            
            state.errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ErrorOutline,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "Erro ao carregar anúncio",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = state.errorMessage ?: "Não foi possível carregar o anúncio. Verifique sua conexão e tente novamente.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = { onEvent(OfferorHomeEvent.LoadAnuncio) }) {
                            Text("Tentar novamente")
                        }
                    }
                }
            }
            
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AnuncioContent(
    anuncio: Anuncio,
    isLoading: Boolean,
    onEvent: (OfferorHomeEvent) -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatusBadge(status = anuncio.status)

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = anuncio.titulo,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )

                Text(
                    text = anuncio.descricao,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                HorizontalDivider()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = anuncio.enderecoCompleto,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                HorizontalDivider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ValorCard("Aluguel", anuncio.valorAluguel, Icons.Outlined.Home)
                    ValorCard("Contas", anuncio.valorContas, Icons.Outlined.Receipt)
                    ValorCard("Total", anuncio.valorTotal, Icons.Outlined.AttachMoney, true)
                }

                HorizontalDivider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoItem("Tipo", anuncio.tipoImovel.replace("_", " ").lowercase().replaceFirstChar { it.uppercaseChar() })
                    InfoItem("Vagas", "${anuncio.vagasDisponiveis} disponível(is)")
                }

                if (anuncio.comodos.isNotEmpty()) {
                    HorizontalDivider()
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Cômodos",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            anuncio.comodos.forEach { comodo ->
                                AssistChip(
                                    onClick = { },
                                    label = { 
                                        Text(
                                            text = comodo.replace("_", " ").lowercase().replaceFirstChar { it.uppercaseChar() },
                                            style = MaterialTheme.typography.labelSmall
                                        ) 
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                    )
                                )
                            }
                        }
                    }
                }

                if (anuncio.fotos.isNotEmpty()) {
                    HorizontalDivider()
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Fotos",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            anuncio.fotos.take(3).forEach { fotoUrl ->
                                AsyncImage(
                                    model = fotoUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(100.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }

        ActionButtons(
            anuncio = anuncio,
            isLoading = isLoading,
            onPausarClick = { onEvent(OfferorHomeEvent.PausarAnuncio) },
            onReativarClick = { onEvent(OfferorHomeEvent.ReativarAnuncio) },
            onEditarClick = {
                onEvent(OfferorHomeEvent.EditarAnuncio)
                onEditClick()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun StatusBadge(status: StatusAnuncio) {
    val (text, color) = when (status) {
        StatusAnuncio.ATIVO -> "Ativo" to Color(0xFF12B76A)
        StatusAnuncio.PAUSADO -> "Pausado" to Color(0xFFFF9800)
        StatusAnuncio.INATIVO -> "Inativo" to Color(0xFF757575)
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.15f)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = color
        )
    }
}

@Composable
private fun ValorCard(
    label: String,
    valor: Double,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isHighlighted: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isHighlighted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = "R$ %.0f".format(valor),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = if (isHighlighted) 18.sp else 16.sp
            ),
            color = if (isHighlighted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun InfoItem(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
private fun ActionButtons(
    anuncio: Anuncio,
    isLoading: Boolean,
    onPausarClick: () -> Unit,
    onReativarClick: () -> Unit,
    onEditarClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        GradientButton(
            text = if (isLoading) "Carregando..." else "Editar Vaga",
            buttonTextSize = 16,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                if (!isLoading) {
                    onEditarClick()
                }
            }
        )

        if (anuncio.isAtivo) {
            OutlinedButton(
                onClick = { if (!isLoading) onPausarClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Pause,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Pausar Vaga")
            }
        } else {
            OutlinedButton(
                onClick = { if (!isLoading) onReativarClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Reativar Vaga")
            }
        }
    }
}

@RoomiePreview
@Composable
private fun OfferorHomeScreenPreview() {
    Roomie_AndroidTheme {
        val mockAnuncio = Anuncio(
            id = 1,
            titulo = "Quarto em apartamento próximo à UFC",
            descricao = "Ambiente tranquilo, ideal para estudantes.",
            rua = "Rua dos Universitários",
            numero = "123",
            bairro = "Benfica",
            cidade = "Fortaleza",
            estado = "CE",
            valorAluguel = 850.0,
            valorContas = 200.0,
            vagasDisponiveis = 1,
            tipoImovel = "APARTAMENTO",
            fotos = emptyList(),
            comodos = listOf("SALA_DE_ESTAR", "COZINHA", "BANHEIRO", "QUARTO"),
            status = StatusAnuncio.ATIVO
        )
        
        OfferorHomeScreen(
            state = OfferorHomeState(anuncio = mockAnuncio),
            onEvent = { }
        )
    }
}
