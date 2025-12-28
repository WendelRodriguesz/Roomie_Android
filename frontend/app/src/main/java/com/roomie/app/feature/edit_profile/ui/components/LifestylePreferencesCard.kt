package com.roomie.app.feature.edit_profile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    cleanlinessLevel: Int,
    onCleanlinessLevelChange: (Int) -> Unit,
    socialLevel: Int,
    onSocialLevelChange: (Int) -> Unit,
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


            HorizontalDivider()

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Rotina de sono",
                    style = MaterialTheme.typography.bodyMedium
                )
                SleepRoutineSelector(
                    selected = sleepRoutine,
                    onSelect = onSleepRoutineChange
                )
            }

            HorizontalDivider()

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Frequência de festas",
                    style = MaterialTheme.typography.bodyMedium
                )
                PartyFrequencySelector(
                    selected = partyFrequency,
                    onSelect = onPartyFrequencyChange
                )
            }

            HorizontalDivider()

            PreferenceSlider(
                label = "Nível de limpeza",
                value = cleanlinessLevel,
                onValueChange = onCleanlinessLevelChange,
                valueRange = 1f..5f,
                steps = 3,
                maxValue = 5
            )

            PreferenceSlider(
                label = "Nível social",
                value = socialLevel,
                onValueChange = onSocialLevelChange,
                valueRange = 1f..5f,
                steps = 3,
                maxValue = 5
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
            sleepRoutine = SleepRoutine.FLEXIBLE,
            onSleepRoutineChange = {},
            partyFrequency = PartyFrequency.SOMETIMES,
            onPartyFrequencyChange = {},
            cleanlinessLevel = 4,
            onCleanlinessLevelChange = {},
            socialLevel = 3,
            onSocialLevelChange = {}
        )
    }
}
