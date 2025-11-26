package com.roomie.app.feature.edit_profile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.PartyFrequency

@Composable
fun PartyFrequencySelector(
    selected: PartyFrequency,
    onSelect: (PartyFrequency) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        PartyFrequency.entries.forEach { freq ->
            val isSelected = freq == selected
            FilterChip(
                onClick = { onSelect(freq) },
                label = { 
                    Text(
                        when (freq) {
                            PartyFrequency.NEVER -> "Nunca"
                            PartyFrequency.SOMETIMES -> "Às vezes"
                            PartyFrequency.FREQUENT -> "Frequentemente"
                        }
                    ) 
                },
                selected = isSelected,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@RoomiePreview
@Composable
private fun PartyFrequencySelectorPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        PartyFrequencySelector(
            selected = PartyFrequency.SOMETIMES,
            onSelect = {}
        )
    }
}

