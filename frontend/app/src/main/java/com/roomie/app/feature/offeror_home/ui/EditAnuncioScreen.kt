package com.roomie.app.feature.offeror_home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.components.LabeledOutlinedField
import com.roomie.app.core.ui.components.RoomieSelect

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
    isSaving: Boolean = false,
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
    onCancelClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    errorMessage: String? = null
) {
    val tiposImovel = remember {
        listOf("CASA", "APARTAMENTO", "KITNET", "REPUBLICA", "OUTROS")
    }

    val comodosDisponiveis = remember {
        listOf("SALA", "COZINHA", "QUARTO", "BANHEIRO", "VARANDA", "GARAGEM", "LAVANDERIA", "ESCRITORIO")
    }

    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    var comodosSelecionados by remember(comodos) { mutableStateOf(comodos.toMutableSet()) }

    // Atualiza os cômodos selecionados quando o anúncio é carregado
    LaunchedEffect(comodos) {
        comodosSelecionados = comodos.toMutableSet()
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            android.util.Log.d("EditAnuncioScreen", "📢 Mostrando mensagem de erro: $message")
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Anúncio") },
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
                                android.util.Log.d("EditAnuncioScreen", "Cômodo $comodo: $checked")
                                if (checked) {
                                    comodosSelecionados.add(comodo)
                                } else {
                                    comodosSelecionados.remove(comodo)
                                }
                                val novosComodos = comodosSelecionados.toList()
                                android.util.Log.d("EditAnuncioScreen", "Novos cômodos selecionados: $novosComodos")
                                onComodosChange(novosComodos)
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = comodo.lowercase().replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
