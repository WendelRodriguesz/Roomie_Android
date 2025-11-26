package com.roomie.app.feature.edit_profile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.SleepRoutine

@Composable
fun SleepRoutineSelector(
    selected: SleepRoutine,
    onSelect: (SleepRoutine) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        SleepRoutine.entries.forEach { routine ->
            val isSelected = routine == selected
            FilterChip(
                onClick = { onSelect(routine) },
                label = { 
                    Text(
                        when (routine) {
                            SleepRoutine.MORNING -> "Matutino"
                            SleepRoutine.NIGHT -> "Noturno"
                            SleepRoutine.FLEXIBLE -> "Flexível"
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
private fun SleepRoutineSelectorPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        SleepRoutineSelector(
            selected = SleepRoutine.FLEXIBLE,
            onSelect = {}
        )
    }
}