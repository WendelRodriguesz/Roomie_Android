package com.roomie.app.feature.notifications.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.material3.HorizontalDivider
import coil3.compose.AsyncImage
import com.roomie.app.feature.notifications.data.model.Match
import kotlin.math.abs

private operator fun <T> Comparable<T>.compareTo(other: T): Int = this.compareTo(other)

@Composable
fun MatchNotificationCard(
    match: Match,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    val interessado = match.interessado
    val isPending = match.status == "PENDENTE"
    
    val density = LocalDensity.current
    var offsetX by remember { mutableStateOf(0f) }
    val swipeThresholdPx = with(density) { 120.dp.toPx() }
    
    val alpha by animateFloatAsState(
        targetValue = if (abs(offsetX) > 20f) 0.7f else 1f,
        animationSpec = tween(200),
        label = "cardAlpha"
    )
    
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isPending && abs(offsetX) > 20f) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .background(
                        color = if (offsetX > 0) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = if (offsetX > 0) Alignment.CenterStart else Alignment.CenterEnd
            ) {
                Icon(
                    if (offsetX > 0) Icons.Outlined.Check else Icons.Outlined.Close,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .size(48.dp)
                )
            }
        }
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = with(density) { offsetX.toDp() })
                .alpha(alpha)
                .then(
                    if (isPending) {
                        Modifier.pointerInput(Unit) {
                            detectDragGestures(
                                onDragEnd = {
                                    when {
                                        offsetX > swipeThresholdPx -> {
                                            onAccept()
                                        }
                                        offsetX < -swipeThresholdPx -> {
                                            onReject()
                                        }
                                    }
                                    offsetX = 0f
                                },
                                onDrag = { _, dragAmount ->
                                    offsetX = (offsetX + dragAmount.x).coerceIn(-300f, 300f)
                                }
                            )
                        }
                    } else {
                        Modifier
                    }
                ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            onClick = onToggleExpand
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .animateContentSize(animationSpec = tween(300)),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!interessado.fotoDePerfil.isNullOrBlank()) {
                            AsyncImage(
                                model = interessado.fotoDePerfil,
                                contentDescription = "Foto de ${interessado.nome}",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(48.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Outlined.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${interessado.nome} lhe enviou uma solicitação de interesse",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = when (match.status) {
                                    "PENDENTE" -> "Aguardando sua resposta"
                                    "ACEITO" -> "Match aceito"
                                    "RECUSADO" -> "Match recusado"
                                    else -> match.status
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = when (match.status) {
                                    "PENDENTE" -> MaterialTheme.colorScheme.primary
                                    "ACEITO" -> Color(0xFF4CAF50)
                                    "RECUSADO" -> MaterialTheme.colorScheme.error
                                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                                },
                                fontSize = 12.sp
                            )
                        }
                    }
                    
                    Icon(
                        if (isExpanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                        contentDescription = if (isExpanded) "Recolher" else "Expandir",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                if (isExpanded) {
                    HorizontalDivider()
                    
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoSection(
                            title = "Informações Pessoais",
                            items = listOf(
                                "Idade" to "${interessado.idade} anos",
                                "Cidade" to interessado.cidade,
                                "Ocupação" to interessado.ocupacao,
                                "Gênero" to interessado.genero
                            )
                        )
                        
                        if (interessado.bio.isNotBlank()) {
                            Column {
                                Text(
                                    text = "Sobre",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = interessado.bio,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        InfoSection(
                            title = "Interesses e Preferências",
                            items = listOf(
                                "Frequência de festas" to interessado.interesses.frequenciaFestas,
                                "Hábitos de limpeza" to interessado.interesses.habitosLimpeza,
                                "Horário de sono" to interessado.interesses.horarioSono,
                                "Aceita pets" to if (interessado.interesses.aceitaPets) "Sim" else "Não",
                                "Aceita dividir quarto" to if (interessado.interesses.aceitaDividirQuarto) "Sim" else "Não"
                            ) + listOfNotNull(
                                interessado.interesses.orcamentoMin?.let { min ->
                                    interessado.interesses.orcamentoMax?.let { max ->
                                        "Orçamento" to "R$ ${min.toInt()} - R$ ${max.toInt()}/mês"
                                    }
                                }
                            )
                        )
                    }
                }
                
                if (isPending && isExpanded) {
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onReject,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Recusar")
                        }
                        
                        Button(
                            onClick = onAccept,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            )
                        ) {
                            Icon(
                                Icons.Outlined.Check,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Aceitar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoSection(
    title: String,
    items: List<Pair<String, String>>
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        items.forEach { (label, value) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

