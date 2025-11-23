package com.roomie.app.feature.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.UserMock
import com.roomie.app.feature.profile.model.UserProfile
import com.roomie.app.feature.profile.ui.components.SettingSwitch

@Composable
fun SettingsCard(
    profile: UserProfile,
    onLogoutClick: () -> Unit,
) {
    val settings = profile.settings

    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Configurações",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            SettingSwitch(
                label = "Notificações",
                checked = settings.notificationsEnabled,
                onCheckedChange = {}
            )

            SettingSwitch(
                label = "Aparecer como online",
                checked = settings.showAsOnline,
                onCheckedChange = {}
            )

            SettingSwitch(
                label = "Descoberta",
                checked = settings.discoveryEnabled,
                onCheckedChange = {}
            )

            SettingSwitch(
                label = "Modo escuro",
                checked = settings.darkModeEnabled,
                onCheckedChange = {}
            )

            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

            TextButton(
                onClick = onLogoutClick,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(
                    text = "Sair da conta",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@RoomiePreview
@Composable
private fun SettingsCardPreview(){
    Roomie_AndroidTheme (dynamicColor = false){
        val onLogoutClick: () -> Unit = {}
        SettingsCard(UserMock.profileRoomie1, onLogoutClick)
    }
}