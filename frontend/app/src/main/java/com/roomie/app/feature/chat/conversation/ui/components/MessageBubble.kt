package com.roomie.app.feature.chat.conversation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.roomie.app.feature.chat.model.Mensagem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun MessageBubble(
    mensagem: Mensagem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (mensagem.isMine) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (mensagem.isMine) 16.dp else 4.dp,
                        bottomEnd = if (mensagem.isMine) 4.dp else 16.dp
                    )
                ),
            color = if (mensagem.isMine) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
            } else {
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.15f)
            },
            tonalElevation = if (mensagem.isMine) 2.dp else 1.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    text = mensagem.conteudo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (mensagem.isMine) {

                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatarData(mensagem.enviadaEm),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (mensagem.isMine) {
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    },
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

private fun formatarData(dataString: String): String {
    return try {
        val localDateTime = parsearParaLocalDateTime(dataString) ?: return "00:00"
        val hoje = LocalDateTime.now()
        val hojeDate = hoje.toLocalDate()
        val mensagemDate = localDateTime.toLocalDate()
        
        return if (mensagemDate == hojeDate) {
            localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        } else if (mensagemDate.year == hojeDate.year) {
            localDateTime.format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))
        } else {
            localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        }
    } catch (e: Exception) {
        "00:00"
    }
}

private fun parsearParaLocalDateTime(dataString: String): LocalDateTime? {
    return try {
        try {
            val instant = java.time.Instant.parse(dataString)
            return LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
        } catch (e: Exception) {
        }
        
        val formatos = listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss.S",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm"
        )
        
        for (formato in formatos) {
            try {
                val formatter = DateTimeFormatter.ofPattern(formato)
                val localDateTime = LocalDateTime.parse(dataString, formatter)
                val instant = localDateTime.atZone(java.time.ZoneId.of("UTC")).toInstant()
                return LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
            } catch (e: DateTimeParseException) {
                continue
            }
        }

        val partes = dataString.split("T")
        if (partes.size >= 2) {
            try {
                val dataParte = partes[0].split("-")
                val horaParte = partes[1].split(":").mapNotNull { it.split(".")[0].toIntOrNull() }
                
                if (dataParte.size == 3 && horaParte.size >= 2) {
                    val ano = dataParte[0].toIntOrNull() ?: 1970
                    val mes = dataParte[1].toIntOrNull() ?: 1
                    val dia = dataParte[2].toIntOrNull() ?: 1
                    val hora = horaParte[0]
                    val minuto = horaParte.getOrElse(1) { 0 }
                    val segundo = horaParte.getOrElse(2) { 0 }
                    
                    val utcDateTime = LocalDateTime.of(ano, mes, dia, hora, minuto, segundo)
                    val instant = utcDateTime.atZone(java.time.ZoneId.of("UTC")).toInstant()
                    return LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
                }
            } catch (e: Exception) {
            }
        }

        null
    } catch (e: Exception) {
        null
    }
}