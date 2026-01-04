package com.roomie.app.feature.vaga.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyListingsScreen(
    onCreateListingClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Minhas vagas",
            style = MaterialTheme.typography.titleLarge
        )
        Text(text = "Tela do ofertante (gerenciar vagas) — em construção.")

        Button(onClick = onCreateListingClick) {
            Text("Cadastrar nova vaga")
        }
    }
}
