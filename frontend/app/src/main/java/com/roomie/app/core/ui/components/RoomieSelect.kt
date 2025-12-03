package com.roomie.app.core.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> RoomieSelect(
    value: T?,
    onValueChange: (T) -> Unit,
    items: List<T>,
    itemLabel: (T) -> String,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {

        TextField(
            value = value?.let { itemLabel(it) } ?: "",
            onValueChange = {},
            readOnly = true,
            placeholder = { Text(placeholder, fontSize = 14.sp) },
            singleLine = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                cursorColor = MaterialTheme.colorScheme.scrim
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(text = itemLabel(item))
                    },
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
