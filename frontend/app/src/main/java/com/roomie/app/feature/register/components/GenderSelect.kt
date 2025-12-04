package com.roomie.app.feature.register.components

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.roomie.app.core.ui.components.RoomieSelect
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.GenderOption

@Composable
fun GenderSelect(
    value: GenderOption?,
    onValueChange: (GenderOption) -> Unit,
    modifier: Modifier = Modifier
) {
    RoomieSelect(
        value = value,
        onValueChange = onValueChange,
        items = GenderOption.entries,
        itemLabel = { it.label },
        placeholder = "Selecione o gênero",
        modifier = modifier
    )
}

@RoomiePreview
@Composable
private fun GenderSelectPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        var gender by remember { mutableStateOf<GenderOption?>(null) }

        Surface {
            GenderSelect(
                value = gender,
                onValueChange = { gender = it }
            )
        }
    }
}
