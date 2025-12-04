package com.roomie.app.feature.preference_registration.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.CleaningHabit

@Composable
fun CleaningHabitSelector(
    selected: CleaningHabit?,
    onSelect: (CleaningHabit) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        CleaningHabit.entries.forEach { habit ->
            val isSelected = habit == selected
            FilterChip(
                onClick = { onSelect(habit) },
                label = {
                    Text(
                        text = when (habit) {
                            CleaningHabit.DAILY -> "Diário"
                            CleaningHabit.WEEKLY -> "Semanal"
                            CleaningHabit.BIWEEKLY -> "Quinzenal"
                            CleaningHabit.OCCASIONAL -> "Ocasional"
                        },
                        maxLines = 1
                    )
                },
                selected = isSelected
            )
        }
    }
}

@RoomiePreview
@Composable
private fun CleaningHabitSelectorPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        CleaningHabitSelector(
            selected = CleaningHabit.WEEKLY,
            onSelect = {}
        )
    }
}

