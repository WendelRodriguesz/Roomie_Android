package com.roomie.app.feature.match.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FmdGood
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.roomie.app.R
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.match.model.ListingCard
import com.roomie.app.feature.match.model.ListingStatus

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
            val photoUrl = listing.photos.firstOrNull()
            val ctx = LocalContext.current

            when {
                isPreview -> {
                    Image(
                        painter = painterResource(R.drawable.match1),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                listing.localPhoto != null -> {
                    Image(
                        painter = painterResource(listing.localPhoto!!),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                !photoUrl.isNullOrBlank() -> {
                    if (photoUrl.startsWith("http://") || photoUrl.startsWith("https://")) {
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.match1),
                            placeholder = painterResource(R.drawable.match1)
                        )
                    } else {
                        val id = ctx.resources.getIdentifier(photoUrl, "drawable", ctx.packageName)
                        if (id != 0) {
                            Image(
                                painter = painterResource(id),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(Modifier.fillMaxSize().background(Color(0xFFE0E0E0)))
                        }
                    }
                }
                else -> {
                    Box(Modifier.fillMaxSize().background(Color(0xFFE0E0E0)))
                }
            }


            Box(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(240.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x80000000),
                                Color(0xCC000000),
                                Color(0xFF000000)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    listing.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Outlined.FmdGood,
                            contentDescription = "Localização",
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFE0E0E0)
                        )
                        Text(
                            "${listing.neighborhood}, ${listing.city}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFE0E0E0),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    AssistChip(
                        onClick = onSeeMore,
                        label = { 
                            Text(
                                "R$ %.0f".format(listing.totalRent),
                                fontWeight = FontWeight.SemiBold
                            ) 
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color(0xFF9BD32B).copy(alpha = 0.25f),
                            labelColor = Color(0xFF9BD32B),
                            leadingIconContentColor = Color(0xFF9BD32B)
                        ),
                        border = null,
                        shape = RoundedCornerShape(20.dp)
                    )
                }

                Text(
                    listing.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFE5E5E5),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )

                if (listing.tags.isNotEmpty()) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listing.tags.forEach { tag ->
                            TagChip(tag)
                        }
                    }
                }
            }

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
                bedrooms = 2,
                bathrooms = 2,
                areaInSquareMeters = 80,
                acceptPets = true,
                rules = "Sem festas após 22h",
                status = ListingStatus.ACTIVE,
                createdInMillis = 0L,
                rating = 4.8,
                tags = listOf("2 quartos", "Piscina", "Academia"),
                photos = listOf("match1")
            )
            MatchCard(mock, {}, {}, {})
        }
    }
}

@Composable
private fun TagChip(text: String) {
    SuggestionChip(
        onClick = {},
        label = { 
            Text(
                text, 
                color = Color(0xFFE0E0E0),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium
            ) 
        },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = Color.White.copy(alpha = 0.25f),
            labelColor = Color(0xFFE0E0E0)
        ),
        border = null,
        shape = RoundedCornerShape(16.dp)
    )
}

