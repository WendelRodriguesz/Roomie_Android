package com.roomie.app.core.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.RoomieGradient
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme

@Composable
fun GradientButton(
    text: String,
    modifier: Modifier = Modifier,
    buttonTextSize: Int,
    onClick: () -> Unit = { }
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .background(RoomieGradient)
                .then(modifier),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, fontSize = buttonTextSize.sp, color = MaterialTheme.colorScheme.inverseOnSurface)
        }
    }
}

@RoomiePreview
@Composable
private fun GradientButtonPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        Surface {
            GradientButton(
                text = "Continuar",
                buttonTextSize = 16,
                modifier = Modifier.size(width = 200.dp, height = 48.dp)
            )
        }
    }
}

