package com.roomie.app.feature.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.profile.model.UserProfile

@Composable
fun MatchCard(
    profile: UserProfile,
    onMatchesClick: () -> Unit,
    onMyListingsClick: () -> Unit,
    onLikedListingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Vagas",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            TextButton(
                onClick = onMatchesClick,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Matchs")
            }

            if (profile.role == ProfileRole.OFFEROR) {
                TextButton(
                    onClick = onMyListingsClick,
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text("Vagas anunciadas")
                }
            } else {
                TextButton(
                    onClick = onLikedListingsClick,
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text("Vagas curtidas")
                }
            }
        }
    }
}

@RoomiePreview
@Composable
private fun MatchCardPreview() {
    Roomie_AndroidTheme (dynamicColor = false){
        MatchCard(
            profile = com.roomie.app.feature.profile.model.UserMock.profileRoomie1,
            onMatchesClick = {},
            onMyListingsClick = {},
            onLikedListingsClick = {}
        )
    }
}
