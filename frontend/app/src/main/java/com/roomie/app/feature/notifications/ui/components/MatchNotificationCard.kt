package com.roomie.app.feature.notifications.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import coil3.compose.AsyncImage
import com.roomie.app.feature.notifications.data.model.Match

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
    
    Card(
        modifier = Modifier.fillMaxWidth(),
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
            // Header - sempre visível
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
                    // Avatar
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
            
            // Conteúdo expandido
            if (isExpanded) {
                Divider()
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Informações básicas
                    InfoSection(
                        title = "Informações Pessoais",
                        items = listOf(
                            "Idade" to "${interessado.idade} anos",
                            "Cidade" to interessado.cidade,
                            "Ocupação" to interessado.ocupacao,
                            "Gênero" to interessado.genero
                        )
                    )
                    
                    // Bio
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
                    
                    // Interesses
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
            
            // Botões de ação (apenas para PENDENTE)
            if (isPending && isExpanded) {
                Divider()
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

@Composable
private fun InfoSection(
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

