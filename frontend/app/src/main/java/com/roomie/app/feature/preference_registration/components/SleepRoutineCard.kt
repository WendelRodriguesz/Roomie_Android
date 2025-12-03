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
import com.roomie.app.feature.edit_profile.ui.components.SleepRoutineSelector
import com.roomie.app.feature.profile.model.SleepRoutine

@Composable
fun SleepRoutineCard(
    sleepRoutine: SleepRoutine,
    onSleepRoutineChange: (SleepRoutine) -> Unit,
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
                text = "Horários de sono",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            SleepRoutineSelector(
                selected = sleepRoutine,
                onSelect = onSleepRoutineChange
            )
        }
    }
}

@RoomiePreview
@Composable
private fun SleepRoutineCardPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        SleepRoutineCard(
            sleepRoutine = SleepRoutine.FLEXIBLE,
            onSleepRoutineChange = {}
        )
    }
}

