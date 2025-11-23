package com.roomie.app.feature.match.ui

import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.RoomieGradient
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.match.model.MatchMock
import com.roomie.app.feature.match.presentation.MatchEvent
import com.roomie.app.feature.match.presentation.MatchState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchScreen(
    state: MatchState,
    onEvent: (MatchEvent) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    "Encontre o apartamento ideal",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        shadow = Shadow(
                            color = Color(0xFF757575), offset = Offset(5.0f, 5.0f), blurRadius = 5f
                        )
                    )
                ) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Card grande central
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                MatchCard(
                    listing = state.current,
                    onLeftSideClick = { onEvent(MatchEvent.Prev) },
                    onRightSideClick = { onEvent(MatchEvent.Next) },
                    onSeeMore = { onEvent(MatchEvent.SeeMore) },
                )
            }

            // Ações
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 34.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalIconButton(
                    onClick = { onEvent(MatchEvent.Dislike) },
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = Color(0xFFEA98A0).copy(0.12f),
                        contentColor   = Color(0xFFD86B78)
                    ),
                    modifier = Modifier.size(60.dp)
                    ) {
                    Icon(Icons.Outlined.Close, contentDescription = "Não curti", modifier = Modifier.size(30.dp))
                }
                FilledTonalIconButton(
                    onClick = { onEvent(MatchEvent.Save) },
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = Color(0xFFC1AB75).copy(0.12f),
                        contentColor   = Color(0xFFFFC107)
                    ), modifier = Modifier.size(60.dp)
                ) {
                    Icon(Icons.Outlined.Star, contentDescription = "Salvar", modifier = Modifier.size(30.dp))
                }
                FilledIconButton(
                    onClick = { onEvent(MatchEvent.Like) },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color(0xFFE91E63),
                        contentColor   = Color.White
                    ),modifier = Modifier.size(60.dp)) {
                    Icon(Icons.Outlined.Favorite, contentDescription = "Curti", modifier = Modifier.size(30.dp))
                }
            }
        }
    }
}

@RoomiePreview
@Composable
private fun MatchScreenPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val s = MatchState(items = MatchMock.items)
            MatchScreen(state = s, onEvent = {})
        }
    }
}