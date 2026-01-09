package com.roomie.app.feature.offeror_home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.roomie.app.core.ui.components.LabeledOutlinedField
import com.roomie.app.core.ui.components.RoomieSelect
import com.roomie.app.feature.vaga.ui.components.ImageGalleryCard

private fun formatarNomeComodo(comodo: String): String {
    return when (comodo) {
        "SALA_DE_ESTAR" -> "Sala de Estar"
        "SALA_DE_JANTAR" -> "Sala de Jantar"
        "COZINHA" -> "Cozinha"
        "BANHEIRO" -> "Banheiro"
        "QUARTO" -> "Quarto"
        "LAVANDERIA" -> "Lavanderia"
        "GARAGEM" -> "Garagem"
        "VARANDA" -> "Varanda"
        else -> comodo.lowercase().replace("_", " ").replaceFirstChar { it.uppercase() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAnuncioScreen(
    titulo: String,
    descricao: String,
    rua: String,
    numero: String,
    bairro: String,
    cidade: String,
    estado: String,
    valorAluguel: String,
    valorContas: String,
    vagasDisponiveis: String,
    tipoImovel: String,
    comodos: List<String>,
    fotos: List<String> = emptyList(),
    isSaving: Boolean = false,
    isUploadingPhoto: Boolean = false,
    onTituloChange: (String) -> Unit = {},
    onDescricaoChange: (String) -> Unit = {},
    onRuaChange: (String) -> Unit = {},
    onNumeroChange: (String) -> Unit = {},
    onBairroChange: (String) -> Unit = {},
    onCidadeChange: (String) -> Unit = {},
    onEstadoChange: (String) -> Unit = {},
    onValorAluguelChange: (String) -> Unit = {},
    onValorContasChange: (String) -> Unit = {},
    onVagasDisponiveisChange: (String) -> Unit = {},
    onTipoImovelChange: (String) -> Unit = {},
    onComodosChange: (List<String>) -> Unit = {},
    onFotoAdded: () -> Unit = {},
    onFotoRemoved: (String) -> Unit = {},
    onCancelClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    errorMessage: String? = null,
    successMessage: String? = null
) {
    val tiposImovel = remember {
        listOf("CASA", "APARTAMENTO", "KITNET", "REPUBLICA", "OUTROS")
    }

    val comodosDisponiveis = remember {
        listOf(
            "SALA_DE_ESTAR",
            "SALA_DE_JANTAR",
            "COZINHA",
            "BANHEIRO",
            "QUARTO",
            "LAVANDERIA",
            "GARAGEM",
            "VARANDA"
        )
    }

    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    var comodosSelecionados by remember(comodos) { mutableStateOf(comodos.toMutableSet()) }

    LaunchedEffect(comodos) {
        comodosSelecionados = comodos.toMutableSet()
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long
            )
        }
    }

    LaunchedEffect(successMessage) {
        successMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar vaga") },
                actions = {
                    TextButton(onClick = onCancelClick) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        enabled = !isSaving,
                        onClick = onSaveClick
                    ) {
                        Text(if (isSaving) "Salvando..." else "Salvar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        snackbarHost = { 
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ImageGalleryCardForUrls(
                imageUrls = fotos,
                onAddClick = onFotoAdded,
                onRemoveClick = { index -> onFotoRemoved(fotos[index]) },
                maxImages = 6,
                isUploading = isUploadingPhoto
            )

            LabeledOutlinedField(
                title = "Título",
                value = titulo,
                onValueChange = onTituloChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            LabeledOutlinedField(
                title = "Descrição",
                value = descricao,
                onValueChange = onDescricaoChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                minLines = 3,
                maxLines = 6
            )

            HorizontalDivider()

            Text(
                text = "Endereço",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            LabeledOutlinedField(
                title = "Rua",
                value = rua,
                onValueChange = onRuaChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LabeledOutlinedField(
                    title = "Número",
                    value = numero,
                    onValueChange = onNumeroChange,
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                LabeledOutlinedField(
                    title = "Bairro",
                    value = bairro,
                    onValueChange = onBairroChange,
                    modifier = Modifier.weight(2f),
                    singleLine = true
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LabeledOutlinedField(
                    title = "Cidade",
                    value = cidade,
                    onValueChange = onCidadeChange,
                    modifier = Modifier.weight(2f),
                    singleLine = true
                )
                LabeledOutlinedField(
                    title = "Estado",
                    value = estado,
                    onValueChange = onEstadoChange,
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            HorizontalDivider()

            Text(
                text = "Valores e Informações",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LabeledOutlinedField(
                    title = "Valor do Aluguel",
                    value = valorAluguel,
                    onValueChange = onValorAluguelChange,
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                LabeledOutlinedField(
                    title = "Valor das Contas",
                    value = valorContas,
                    onValueChange = onValorContasChange,
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            LabeledOutlinedField(
                title = "Vagas Disponíveis",
                value = vagasDisponiveis,
                onValueChange = onVagasDisponiveisChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            HorizontalDivider()

            Text(
                text = "Tipo de Imóvel",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            RoomieSelect(
                value = tipoImovel.takeIf { it.isNotBlank() },
                onValueChange = onTipoImovelChange,
                items = tiposImovel,
                itemLabel = { it },
                placeholder = "Selecione o tipo de imóvel",
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider()

            Text(
                text = "Cômodos",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                comodosDisponiveis.forEach { comodo ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = comodosSelecionados.contains(comodo),
                            onCheckedChange = { checked ->
                                if (checked) {
                                    comodosSelecionados.add(comodo)
                                } else {
                                    comodosSelecionados.remove(comodo)
                                }
                                onComodosChange(comodosSelecionados.toList())
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = formatarNomeComodo(comodo),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun ImageGalleryCardForUrls(
    imageUrls: List<String>,
    onAddClick: () -> Unit,
    onRemoveClick: (Int) -> Unit,
    maxImages: Int = 6,
    isUploading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Fotos do imóvel",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (imageUrls.size < maxImages) {
                    item {
                        AddImageButtonForUrls(
                            onClick = onAddClick,
                            modifier = Modifier.size(110.dp),
                            isLoading = isUploading
                        )
                    }
                }

                itemsIndexed(imageUrls) { index, imageUrl ->
                    ImageThumbnailForUrl(
                        imageUrl = imageUrl,
                        onRemoveClick = { onRemoveClick(index) },
                        modifier = Modifier.size(110.dp)
                    )
                }
            }

            if (imageUrls.isNotEmpty()) {
                Text(
                    text = "${imageUrls.size} foto(s) selecionada(s)${if (imageUrls.size < maxImages) " • Adicione até ${maxImages} fotos" else ""}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = "Adicione fotos do imóvel",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun AddImageButtonForUrls(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .clickable(enabled = !isLoading, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar foto",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Adicionar",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun ImageThumbnailForUrl(
    imageUrl: String,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .size(24.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.error)
                .clickable(onClick = onRemoveClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remover foto",
                tint = MaterialTheme.colorScheme.onError,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
