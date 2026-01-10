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
        val formatos = listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss.S",
            "yyyy-MM-dd'T'HH:mm"
        )
        
        var localDateTime: LocalDateTime? = null
        for (formato in formatos) {
            try {
                val formatter = DateTimeFormatter.ofPattern(formato)
                localDateTime = LocalDateTime.parse(dataString, formatter)
                break
            } catch (e: DateTimeParseException) {
                continue
            }
        }

        if (localDateTime != null) {
            return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        }

        val partes = dataString.split("T")
        if (partes.size >= 2) {
            val horaCompleta = partes[1]
            val horaMinuto = horaCompleta.split(":").take(2).joinToString(":")
            if (horaMinuto.matches(Regex("\\d{2}:\\d{2}"))) {
                return horaMinuto
            }
        }

        try {
            val instant = java.time.Instant.parse(dataString)
            val localDateTimeFromInstant = LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
            return localDateTimeFromInstant.format(DateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: Exception) {
            "00:00"
        }
    } catch (e: Exception) {
        "00:00"
    }
}