package com.roomie.app.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.roomie.app.R
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.home.model.ListingDetail
import com.roomie.app.feature.home.presentation.ListingDetailEvent
import com.roomie.app.feature.home.presentation.ListingDetailState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingDetailScreen(
    state: ListingDetailState,
    onEvent: (ListingDetailEvent) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Anúncio") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Erro ao carregar",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.error,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { onEvent(ListingDetailEvent.Refresh) }) {
                        Text("Tentar novamente")
                    }
                }
            }
            state.listing != null -> {
                ListingDetailContent(
                    listing = state.listing,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun ListingDetailContent(
    listing: ListingDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        ImageGallery(listing = listing)
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = listing.titulo,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "${listing.bairro}, ${listing.cidade} - ${listing.estado}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                    Text(
                        text = "${listing.rua}, ${listing.numero}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(start = 24.dp)
                    )
                }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InfoCard(
                    title = "Aluguel",
                    value = "R$ %.2f".format(listing.valorAluguel),
                    icon = Icons.Filled.Home,
                    modifier = Modifier.weight(1f)
                )
                InfoCard(
                    title = "Média Contas",
                    value = "R$ %.2f".format(listing.valorContas),
                    icon = Icons.Filled.People,
                    modifier = Modifier.weight(1f)
                )
            }
            
            SectionCard(title = "Descrição") {
                Text(
                    text = listing.descricao,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
            
            SectionCard(title = "Especificações") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    InfoRow(
                        label = "Tipo de Imóvel",
                        value = formatarTipoImovel(listing.tipoImovel)
                    )
                    InfoRow(
                        label = "Vagas Disponíveis",
                        value = "${listing.vagasDisponiveis}"
                    )
                }
            }
            
            if (listing.comodos.isNotEmpty()) {
                SectionCard(title = "Cômodos") {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listing.comodos.forEach { comodo ->
                            TagChip(tag = formatarComodo(comodo))
                        }
                    }
                }
            }
        }
    }
}

private fun formatarComodo(comodo: String): String {
    return comodo.split("_")
        .joinToString(" ") { palavra ->
            palavra.lowercase().replaceFirstChar { it.uppercase() }
        }
}

private fun formatarTipoImovel(tipo: String): String {
    return tipo.lowercase().replaceFirstChar { it.uppercase() }
}

@Composable
private fun ImageGallery(listing: ListingDetail) {
    val photos = listing.fotos
    val isPreview = LocalInspectionMode.current
    val ctx = LocalContext.current
    val listState = rememberLazyListState()
    val currentIndex by remember {
        derivedStateOf {
            if (listState.layoutInfo.visibleItemsInfo.isNotEmpty()) {
                listState.layoutInfo.visibleItemsInfo.first().index
            } else 0
        }
    }
    
    if (photos.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sem imagens disponíveis",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                items(photos.size) { index ->
                    val photo = photos[index]
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillParentMaxHeight()
                    ) {
                        when {
                            isPreview -> {
                                Image(
                                    painter = painterResource(R.drawable.match1),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            photo.startsWith("http://") || photo.startsWith("https://") -> {
                                AsyncImage(
                                    model = photo,
                                    contentDescription = "Imagem ${index + 1} de ${photos.size}",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                    error = painterResource(R.drawable.match1),
                                    placeholder = painterResource(R.drawable.match1)
                                )
                            }
                            else -> {
                                val id = ctx.resources.getIdentifier(photo, "drawable", ctx.packageName)
                                if (id != 0) {
                                    Image(
                                        painter = painterResource(id),
                                        contentDescription = "Imagem ${index + 1} de ${photos.size}",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f))
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            if (photos.size > 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(photos.size) { index ->
                        val isSelected = currentIndex == index
                        Box(
                            modifier = Modifier
                                .size(if (isSelected) 8.dp else 6.dp)
                                .padding(horizontal = 3.dp)
                                .background(
                                    color = if (isSelected) 
                                        MaterialTheme.colorScheme.primary 
                                    else 
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            content()
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    icon: ImageVector? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun TagChip(tag: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
    ) {
        Text(
            text = tag,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@RoomiePreview
@Composable
private fun ListingDetailScreenPreview() {
    Roomie_AndroidTheme {
        Surface {
            val mockListing = ListingDetail(
                id = 1,
                titulo = "Quarto em apartamento próximo à UFC",
                descricao = "Ambiente tranquilo, ideal para estudantes. Próximo a supermercados e parada de ônibus.",
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
                statusAnuncio = "ATIVO"
            )
            ListingDetailContent(listing = mockListing)
        }
    }
}

