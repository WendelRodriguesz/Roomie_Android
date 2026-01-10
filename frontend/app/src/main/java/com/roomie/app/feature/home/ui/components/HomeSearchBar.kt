package com.roomie.app.feature.home.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun HomeSearchBar(
    query: String,
    isFiltering: Boolean,
    hasActiveFilters: Boolean,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 54.dp),
            placeholder = {
                Text(
                    "Buscar vaga por título ou localização",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            shape = MaterialTheme.shapes.large,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            singleLine = true
        )

        FilledIconButton(
            onClick = onFilterClick,
            modifier = Modifier
                .padding(start = 12.dp)
                .heightIn(min = 54.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = if (isFiltering || hasActiveFilters) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                },
                contentColor = if (isFiltering || hasActiveFilters) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Tune,
                contentDescription = "Filtros"
            )
        }
    }
}


