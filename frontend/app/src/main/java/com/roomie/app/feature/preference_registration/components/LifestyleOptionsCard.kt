package com.roomie.app.feature.preference_registration.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.edit_profile.ui.components.PreferenceSwitch

@Composable
fun LifestyleOptionsCard(
    acceptsPets: Boolean,
    onAcceptsPetsChange: (Boolean) -> Unit,
    acceptsRoomSharing: Boolean,
    onAcceptsRoomSharingChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Opções de convivência",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            PreferenceSwitch(
                label = "Aceita pets",
                checked = acceptsPets,
                onCheckedChange = onAcceptsPetsChange
            )

            PreferenceSwitch(
                label = "Aceito dividir quarto",
                checked = acceptsRoomSharing,
                onCheckedChange = onAcceptsRoomSharingChange
            )
        }
    }
}

@RoomiePreview
@Composable
private fun LifestyleOptionsCardPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        LifestyleOptionsCard(
            acceptsPets = true,
            onAcceptsPetsChange = {},
            acceptsRoomSharing = false,
            onAcceptsRoomSharingChange = {}
        )
    }
}

