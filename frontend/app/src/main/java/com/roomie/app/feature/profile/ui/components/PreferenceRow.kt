package com.roomie.app.feature.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme

@Composable
fun PreferenceRow(
    label: String,
    value: Int,
    max: Int = 5,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(max) { index ->
                val filled = index < value
                Icon(
                    imageVector = if (filled) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (filled) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@RoomiePreview
@Composable
private fun PreferenceRowPreview(){
    Roomie_AndroidTheme (dynamicColor = false){
        PreferenceRow(label = "Nível social",
            value = 2)
    }
}