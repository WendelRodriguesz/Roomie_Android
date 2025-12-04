package com.roomie.app.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.ui.text.TextStyle
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
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { 
            Text(
                text = placeholder, 
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            ) 
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (isFocused) 
                    MaterialTheme.colorScheme.primary 
                else 
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        ),
        interactionSource = interactionSource,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
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
