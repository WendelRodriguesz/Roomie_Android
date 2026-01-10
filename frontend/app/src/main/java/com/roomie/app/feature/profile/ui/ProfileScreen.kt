package com.roomie.app.feature.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.AppSettings
import com.roomie.app.core.ui.theme.LocalAppSettings
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.UserMock
import com.roomie.app.feature.profile.model.UserProfile
import com.roomie.app.feature.profile.ui.components.LifestyleCard
import com.roomie.app.feature.profile.ui.components.MatchCard
import com.roomie.app.feature.profile.ui.components.ProfileHeaderCard
import com.roomie.app.feature.profile.ui.components.SettingsCard

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