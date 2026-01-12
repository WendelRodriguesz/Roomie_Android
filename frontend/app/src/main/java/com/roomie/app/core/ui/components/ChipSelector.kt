package com.roomie.app.core.ui.components

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme

@Composable
fun <T> ChipSelector(
    title: String? = null,
    options: List<T>,
    selected: T,
    onSelect: (T) -> Unit,
    labelOf: (T) -> String,
    modifier: Modifier = Modifier
) {
    Column(verticalArrangement = spacedBy(8.dp)) {
        title?.let {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
            FlowRow(
                horizontalArrangement = spacedBy(8.dp),
                verticalArrangement = spacedBy(8.dp),
                modifier = modifier.fillMaxWidth()
            ) {
                options.forEach { option ->
                    val isSelected = option == selected
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelect(option) },
                        label = { Text(labelOf(option), maxLines = 1) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )
                    )
                }
        }
    }
}

@RoomiePreview
@Composable
private fun ChipSelectorPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        val selectedState = remember { mutableStateOf("Opção A") }

        ChipSelector(
            title = "Titulo",
            options = listOf("Opção A", "Opção B", "Opção C"),
            selected = selectedState.value,
            onSelect = { selectedState.value = it },
            labelOf = { it },
            modifier = Modifier.padding(16.dp)
        )
    }
}
