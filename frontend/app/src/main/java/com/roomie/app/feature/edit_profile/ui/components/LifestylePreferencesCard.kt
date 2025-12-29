package com.roomie.app.feature.edit_profile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.components.ChipSelector
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.PartyFrequency
import com.roomie.app.feature.profile.model.SleepRoutine

@Composable
fun LifestylePreferencesCard(
    acceptsPets: Boolean,
    onAcceptsPetsChange: (Boolean) -> Unit,
    isSmoker: Boolean,
    onIsSmokerChange: (Boolean) -> Unit,
    acceptsSharedRoom: Boolean,
    onAcceptsSharedRoomChange: (Boolean) -> Unit,
    sleepRoutine: SleepRoutine,
    onSleepRoutineChange: (SleepRoutine) -> Unit,
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
                text = "Preferências de convivência",
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
                label = "Fumante",
                checked = isSmoker,
                onCheckedChange = onIsSmokerChange
            )

            PreferenceSwitch(
                label = "Aceita dividir quarto",
                checked = acceptsSharedRoom,
                onCheckedChange = onAcceptsSharedRoomChange
            )

            HorizontalDivider()

            ChipSelector(
                title = "Rotina de sono",
                options = SleepRoutine.entries,
                selected = sleepRoutine,
                onSelect = onSleepRoutineChange,
                labelOf = { it.label }
            )

            HorizontalDivider()

            ChipSelector(
                title = "Frequência de festas",
                options = PartyFrequency.entries,
                selected = partyFrequency,
                onSelect = onPartyFrequencyChange,
                labelOf = { it.label }
            )
        }
    }
}

@RoomiePreview
@Composable
private fun LifestylePreferencesCardPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        LifestylePreferencesCard(
            acceptsPets = true,
            onAcceptsPetsChange = {},
            isSmoker = false,
            onIsSmokerChange = {},
            acceptsSharedRoom = false,
            onAcceptsSharedRoomChange = {  },
            sleepRoutine = SleepRoutine.FLEXIVEL,
            onSleepRoutineChange = {},
            partyFrequency = PartyFrequency.AS_VEZES,
            onPartyFrequencyChange = {},
        )
    }
}
