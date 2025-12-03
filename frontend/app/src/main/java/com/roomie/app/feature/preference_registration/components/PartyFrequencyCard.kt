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
import com.roomie.app.feature.edit_profile.ui.components.PartyFrequencySelector
import com.roomie.app.feature.profile.model.PartyFrequency

@Composable
fun PartyFrequencyCard(
    partyFrequency: PartyFrequency,
    onPartyFrequencyChange: (PartyFrequency) -> Unit,
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
                text = "Frequência de festas",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            PartyFrequencySelector(
                selected = partyFrequency,
                onSelect = onPartyFrequencyChange
            )
        }
    }
}

@RoomiePreview
@Composable
private fun PartyFrequencyCardPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        PartyFrequencyCard(
            partyFrequency = PartyFrequency.SOMETIMES,
            onPartyFrequencyChange = {}
        )
    }
}

