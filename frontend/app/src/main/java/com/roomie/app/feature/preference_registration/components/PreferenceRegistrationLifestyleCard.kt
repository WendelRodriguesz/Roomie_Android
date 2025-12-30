package com.roomie.app.feature.preference_registration.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.components.ChipSelector
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.edit_profile.ui.components.PreferenceSwitch
import com.roomie.app.feature.profile.model.CleaningHabit
import com.roomie.app.feature.profile.model.PartyFrequency
import com.roomie.app.feature.profile.model.SleepRoutine

@Composable
fun PreferenceRegistrationLifestyleCard(
    acceptsPets: Boolean,
    onAcceptsPetsChange: (Boolean) -> Unit,
    acceptsSharedRoom: Boolean,
    onAcceptsSharedRoomChange: (Boolean) -> Unit,
    sleepRoutine: SleepRoutine,
    onSleepRoutineChange: (SleepRoutine) -> Unit,
    partyFrequency: PartyFrequency,
    onPartyFrequencyChange: (PartyFrequency) -> Unit,
    cleaningHabit: CleaningHabit,
    onCleaningHabitChange: (CleaningHabit) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionCard(title = "Preferências de convivência") {
            PreferenceSwitch(
                label = "Aceita pets",
                checked = acceptsPets,
                onCheckedChange = onAcceptsPetsChange
            )

            PreferenceSwitch(
                label = "Aceita dividir quarto",
                checked = acceptsSharedRoom,
                onCheckedChange = onAcceptsSharedRoomChange
            )
        }

        SectionCard(title = "Rotina de sono") {
            ChipSelector(
                options = SleepRoutine.entries,
                selected = sleepRoutine,
                onSelect = onSleepRoutineChange,
                labelOf = { it.label }
            )
        }

        SectionCard(title = "Frequência de festas") {
            ChipSelector(
                options = PartyFrequency.entries,
                selected = partyFrequency,
                onSelect = onPartyFrequencyChange,
                labelOf = { it.label }
            )
        }

        SectionCard(title = "Hábitos de limpeza") {
            ChipSelector(
                options = CleaningHabit.entries,
                selected = cleaningHabit,
                onSelect = onCleaningHabitChange,
                labelOf = { it.label }
            )
        }
    }
}
@RoomiePreview
@Composable
private fun PreferenceRegistrationLifestyleCardPreview(){
    Roomie_AndroidTheme (dynamicColor = false){
        PreferenceRegistrationLifestyleCard(
            modifier = Modifier.padding(16.dp),
            acceptsPets = true,
            onAcceptsPetsChange = {  },

            acceptsSharedRoom = false,
            onAcceptsSharedRoomChange = { },

            sleepRoutine = SleepRoutine.FLEXIVEL,
            onSleepRoutineChange = {  },

            partyFrequency = PartyFrequency.AS_VEZES,
            onPartyFrequencyChange = {  },

            cleaningHabit = CleaningHabit.QUINZENAL,
            onCleaningHabitChange = { },
            )
    }
}

