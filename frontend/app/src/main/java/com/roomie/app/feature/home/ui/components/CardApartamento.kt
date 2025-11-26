package com.roomie.app.feature.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    favorito: Boolean,
    aoFavoritar: () -> Unit,
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
                CabecalhoCard(
                    anuncio = anuncio,
                    favorito = favorito,
                    aoFavoritar = aoFavoritar
                )
                Text(
                    "${anuncio.neighborhood}, ${anuncio.city}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.9f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                LinhaEspecificacoes(anuncio = anuncio)
                ChipsComodidades(tags = anuncio.tags)
                RodapeDisponibilidade(anuncio = anuncio)
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
private fun CabecalhoCard(
    anuncio: ListingCard,
    favorito: Boolean,
    aoFavoritar: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.Top
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
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            SeloAvaliacao(avaliacao = anuncio.rating)
            IconButton(
                onClick = aoFavoritar,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        shape = CircleShape
                    )
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = if (favorito) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = if (favorito) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.scrim
                )
            }
        }
    }
}

@Composable
private fun SeloAvaliacao(avaliacao: Double) {
    Surface(
        shape = RoundedCornerShape(50),
        tonalElevation = 1.dp,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "%.1f".format(avaliacao),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun LinhaEspecificacoes(anuncio: ListingCard) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        BlocoEspecificacao(valor = "${anuncio.bedrooms}", rotulo = "quartos")
        BlocoEspecificacao(valor = "${anuncio.bathrooms}", rotulo = "banheiros")
        anuncio.areaInSquareMeters?.let {
            BlocoEspecificacao(valor = "${it}m²", rotulo = "área")
        }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChipsComodidades(tags: List<String>) {
    if (tags.isEmpty()) return

    val maximoVisivel = 3
    val visiveis = tags.take(maximoVisivel)
    val restante = tags.size - visiveis.size

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        visiveis.forEach { tag ->
            AssistChip(
                onClick = {},
                label = { Text(tag) },
                shape = RoundedCornerShape(50),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                )
            )
        }
        if (restante > 0) {
            AssistChip(
                onClick = {},
                label = { Text("+$restante") },
                shape = RoundedCornerShape(50),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                )
            )
        }
    }
}

@Composable
private fun RodapeDisponibilidade(anuncio: ListingCard) {
    val ocupadas = anuncio.numResidents
    val total = anuncio.numResidents + anuncio.vacanciesDisp

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$ocupadas/$total vagas",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFFF5383),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "R$ %.0f/mês".format(anuncio.totalRent),
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF12B76A),
            fontWeight = FontWeight.SemiBold
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
                favorito = false,
                aoFavoritar = {},
                aoClicar = {}
            )
        }
    }
}


