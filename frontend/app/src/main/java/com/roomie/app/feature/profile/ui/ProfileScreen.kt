package com.roomie.app.feature.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.UserMock
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.SleepRoutine
import com.roomie.app.feature.profile.model.UserProfile
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.roomie.app.core.ui.theme.AppSettings
import com.roomie.app.core.ui.theme.LocalAppSettings
import com.roomie.app.feature.profile.ui.components.LifestyleCard
import com.roomie.app.feature.profile.ui.components.ProfileHeaderCard
import com.roomie.app.feature.profile.ui.components.SettingsCard
import com.roomie.app.feature.profile.ui.components.MatchCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profile: UserProfile,
    onEditClick: () -> Unit = {},
    onEditPreferencesClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onMatchesClick: () -> Unit = {},
    onMyListingsClick: () -> Unit = {},
    onLikedListingsClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meu perfil") },
                actions = {
                    TextButton(onClick = onEditClick) { Text("Editar") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileHeaderCard(profile)
            LifestyleCard(
                profile = profile,
                role = profile.role,
                onEditPreferencesClick = onEditPreferencesClick
            )

            MatchCard(
                profile = profile,
                onMatchesClick = onMatchesClick,
                onMyListingsClick = onMyListingsClick,
                onLikedListingsClick = onLikedListingsClick
            )

            SettingsCard(profile, onLogoutClick)
        }
    }
}


@RoomiePreview
@Composable
fun ProfileScreenPreview() {
    val appSettingsState = remember { mutableStateOf(AppSettings()) }

    CompositionLocalProvider(LocalAppSettings provides appSettingsState) {
        Roomie_AndroidTheme(
            dynamicColor = false,
            darkTheme = appSettingsState.value.darkModeEnabled
        ) {
            ProfileScreen(
                profile = UserMock.profileRoomie1,
                onLogoutClick = {},
                onMatchesClick = {},
                onMyListingsClick = {},
                onLikedListingsClick = {}
            )
        }
    }
}