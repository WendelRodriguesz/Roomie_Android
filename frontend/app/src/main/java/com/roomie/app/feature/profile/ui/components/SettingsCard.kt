package com.roomie.app.feature.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.theme.LocalAppSettings
import com.roomie.app.feature.profile.model.UserProfile
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.UserMock
import com.roomie.app.feature.profile.ui.components.SettingSwitch
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.roomie.app.core.ui.theme.AppSettings

@Composable
fun SettingsCard(
    profile: UserProfile,
    onLogoutClick: () -> Unit,
) {
    val appSettingsState = LocalAppSettings.current
    val settings = appSettingsState.value

    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Configurações",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            SettingSwitch(
                label = "Modo escuro",
                checked = settings.darkModeEnabled,
                onCheckedChange = { checked ->
                    appSettingsState.value = settings.copy(darkModeEnabled = checked)
                }
            )

            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

            TextButton(
                onClick = onLogoutClick,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Sair da conta", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@RoomiePreview
@Composable
private fun SettingsCardPreview(){
    val appSettingsState = remember { mutableStateOf(AppSettings()) }

    CompositionLocalProvider(LocalAppSettings provides appSettingsState) {
        Roomie_AndroidTheme(dynamicColor = false) {
            val onLogoutClick: () -> Unit = {}
            SettingsCard(UserMock.profileRoomie1, onLogoutClick)
        }
    }
}