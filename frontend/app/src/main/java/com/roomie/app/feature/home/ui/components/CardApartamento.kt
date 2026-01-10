package com.roomie.app.feature.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.roomie.app.R
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.match.model.ListingCard
import com.roomie.app.feature.match.model.ListingStatus

@Composable
fun CardApartamento(
    anuncio: ListingCard,
    aoClicar: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = aoClicar),
        shape = RoundedCornerShape(28.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MiniaturaApartamento(anuncio = anuncio)

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CabecalhoCard(anuncio = anuncio)
                LinhaLocalizacao(anuncio = anuncio)
                LinhaEspecificacoes(anuncio = anuncio)
            }
        }
    }
}

@Composable
private fun MiniaturaApartamento(anuncio: ListingCard) {
    val corFallback = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
    val shape = RoundedCornerShape(22.dp)
    val painter = lembrarImagemApartamento(anuncio)

    Surface(
        modifier = Modifier
            .width(88.dp)
            .height(96.dp),
        shape = shape,
        tonalElevation = 1.dp
    ) {
        if (painter != null) {
            Image(
                painter = painter,
                contentDescription = anuncio.title,
                contentScale = ContentScale.Crop
            )
        } else {
            Spacer(
                modifier = Modifier
                    .background(corFallback)
                    .fillMaxWidth()
                    .height(96.dp)
            )
        }
    }
}

@Composable
private fun CabecalhoCard(anuncio: ListingCard) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                anuncio.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                anuncio.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            StatusChip(status = anuncio.status)
        }

    }
}

@Composable
private fun LinhaEspecificacoes(anuncio: ListingCard) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        BlocoEspecificacao(
            valor = "R$ %.0f".format(anuncio.totalRent),
            rotulo = "aluguel"
        )
        BlocoEspecificacao(
            valor = "R$ %.0f".format(anuncio.mediaAccounts),
            rotulo = "média contas"
        )
        BlocoEspecificacao(
            valor = "${anuncio.vacanciesDisp}",
            rotulo = "vagas disp."
        )
    }
}

@Composable
private fun BlocoEspecificacao(valor: String, rotulo: String) {
    Column {
        Text(
            text = valor,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = rotulo,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun LinhaLocalizacao(anuncio: ListingCard) {
    Text(
        text = "${anuncio.neighborhood}, ${anuncio.city}",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.9f),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun StatusChip(status: ListingStatus) {
    val (texto, corFundo, corTexto) = when (status) {
        ListingStatus.ACTIVE -> Triple(
            "ATIVO",
            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            MaterialTheme.colorScheme.primary
        )
        ListingStatus.INACTIVE -> Triple(
            "DESATIVADO",
            MaterialTheme.colorScheme.error.copy(alpha = 0.12f),
            MaterialTheme.colorScheme.error
        )
    }

    Surface(
        shape = RoundedCornerShape(50),
        color = corFundo,
        tonalElevation = 0.dp
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = corTexto,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun lembrarImagemApartamento(anuncio: ListingCard) = when {
    LocalInspectionMode.current -> painterResource(R.drawable.match1)
    anuncio.localPhoto != null -> painterResource(anuncio.localPhoto)
    else -> {
        val ctx = LocalContext.current
        val resName = anuncio.photos.firstOrNull()
        if (resName.isNullOrBlank()) null
        else {
            val id = remember(resName) {
                ctx.resources.getIdentifier(resName, "drawable", ctx.packageName)
            }
            if (id != 0) painterResource(id) else null
        }
    }
}

@RoomiePreview
@Composable
private fun CardApartamentoPreview() {
    Roomie_AndroidTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val mock = ListingCard(
                id = "preview",
                offerorUserId = "host",
                title = "Apartamento Minimalista",
                description = "Espaço iluminado com varanda gourmet.",
                neighborhood = "Butantã",
                city = "São Paulo",
                totalRent = 1200.0,
                mediaAccounts = 200.0,
                numResidents = 2,
                vacanciesDisp = 1,
                bedrooms = 3,
                bathrooms = 2,
                areaInSquareMeters = 85,
                acceptPets = true,
                rules = "",
                status = ListingStatus.ACTIVE,
                createdInMillis = 0L,
                rating = 4.8,
                tags = listOf("Wi-Fi", "Lavanderia", "Garagem"),
                localPhoto = R.drawable.match1
            )
            CardApartamento(
                anuncio = mock,
                aoClicar = {}
            )
        }
    }
}


