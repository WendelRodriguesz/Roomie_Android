package com.roomie.app.feature.match.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FmdGood
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PinDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roomie.app.R
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.match.model.ListingCard
import com.roomie.app.feature.match.model.ListingStatus
import com.roomie.app.feature.match.presentation.MatchEvent

@Composable
fun MatchCard(
    listing: ListingCard?,
    onLeftSideClick: () -> Unit,
    onRightSideClick: () -> Unit,
    onSeeMore: () -> Unit
) {
    if (listing == null) return

    val shape = RoundedCornerShape(28.dp)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.60f),
        shape = shape
    ) {
        Box(Modifier
            .fillMaxSize()
        ) {

            val isPreview = LocalInspectionMode.current
            val resName = listing.photos.firstOrNull()

            when {
                // Preview: placeholder seguro
                isPreview -> {
                    Image(
                        painter = painterResource(R.drawable.match1),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                // Runtime: nome -> id (mock local por enquanto)
                !resName.isNullOrBlank() -> {
                    val id = getDrawableId(resName)
                    if (id != 0) {
                        Image(
                            painter = painterResource(id),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize().blur(8.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(Modifier.fillMaxSize().background(Color(0xFFE0E0E0)))
                    }
                }
                else -> Box(Modifier.fillMaxSize().background(Color(0xFFE0E0E0)))
            }

            // gradiente inferior p/ leitura
            Box(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(220.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color.Transparent, Color(0xAA000000), Color(0xCC000000))
                        )
                    )
            )

            // conteúdo textual
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    listing.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFEFEFEF),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically)
                    {
                        Icon(Icons.Outlined.FmdGood, contentDescription = "Localização", modifier = Modifier.size(24.dp), tint = Color(0xFFD3D3D3),)
                        Text(
                            "${listing.neighborhood}, ${listing.city}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFD3D3D3),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        ) }

                    AssistChip(
                        onClick = onSeeMore,
                        label = { Text("R$ %.0f".format(listing.totalRent)) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = Color(0xFF9BD32B).copy(alpha = 0.15f),
                            labelColor     = Color(0xFF9BD32B)
                        ),
                        border = null,
                        shape = RoundedCornerShape(15.dp)
                    )
                }

                Text(
                    listing.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFD1D1D1),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(listing.tags) { tag ->
                        TagChip(
                            tag
                        )
                    }
                }
            }

            // zonas clicáveis laterais (prev/next)
            Row(Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp))
                        .clickable(onClick = onLeftSideClick)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp))
                        .clickable(onClick = onRightSideClick)
                )
            }
        }
    }
}

@Composable
private fun getDrawableId(name: String): Int {
    val ctx = LocalContext.current
    return ctx.resources.getIdentifier(name, "drawable", ctx.packageName)
}

@RoomiePreview
@Composable
private fun MatchCardPreview() {
    Roomie_AndroidTheme {
        Surface {
            val mock = ListingCard(
                id = "1",
                offerorUserId = "u1",
                title = "Apartamento Moderno\nVila Madalena",
                description = "Apto de 2 quartos em localização privilegiada.",
                neighborhood = "Vila Madalena",
                city = "São Paulo",
                totalRent = 1200.0,
                mediaAccounts = 200.0,
                numResidents = 2,
                vacanciesDisp = 1,
                acceptPets = true,
                rules = "Sem festas após 22h",
                status = ListingStatus.ACTIVE,
                createdInMillis = 0L,
                tags = listOf("2 quartos", "Piscina", "Academia"),
                photos = listOf("match1") // tem que existir em res/drawable
            )
            MatchCard(mock, {}, {}, {})
        }
    }
}

@Composable
private fun TagChip(text: String) {
    SuggestionChip(
        onClick = {},
        label = { Text(text, color = Color(0xFFCCCACA)) },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = Color(0xFFFDF5F5).copy(alpha = 0.20f),
            labelColor     = Color(0xFF3E3E3E)
        ),
        border = null,
        shape = RoundedCornerShape(15.dp)
    )
}

