package com.roomie.app.core.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme

@Composable
fun RoomieTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, fontSize = 14.sp) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            cursorColor = MaterialTheme.colorScheme.scrim
        )
    )
}

@RoomiePreview
@Composable
private fun RoomieTextFiedPreview(){
    Roomie_AndroidTheme (dynamicColor = false){
        var text by remember { mutableStateOf("") }
        Surface {
            RoomieTextField(value = "Digite aqui", onValueChange = {text = it},placeholder = "Digite aqui...")
        }
    }
}
