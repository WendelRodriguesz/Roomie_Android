package com.roomie.app.core.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ImagePickerBox(
    imageUri: Uri?,               // a imagem selecionada
    onClick: () -> Unit,          // ação ao clicar no box
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(200.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(2.dp, Color(0xFF444444), RoundedCornerShape(24.dp))
            .background(Color(0xFFF7F7F7))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        if (imageUri == null) {
            // Layout padrão (ícone + texto)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                    tint = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Selecione as\nimagens",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color(0xFF333333)
                )
            }

        } else {
            // Preview usando Coil para carregar o Uri selecionado
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImagePickerBox() {
    ImagePickerBox(
        imageUri = null,
        onClick = {}
    )
}

