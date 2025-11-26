package com.roomie.app.feature.profile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.UserMock
import com.roomie.app.feature.profile.model.UserProfile
import com.roomie.app.feature.profile.ui.ProfileScreen

@Composable
fun ProfileAvatar(profile: UserProfile) {
    val size = 64.dp

    when {
        profile.localPhoto != null -> {
            Image(
                painter = painterResource(id = profile.localPhoto),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .border(width = 1.dp,
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = CircleShape),
                contentScale = ContentScale.Crop,
            )
        }

        profile.photoUrl != null -> {
            AsyncImage(
                model = profile.photoUrl,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .border(width = 1.dp,
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        else -> {
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = profile.name.firstOrNull()?.uppercase() ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
@RoomiePreview
@Composable
fun ProfileAvatarPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        ProfileAvatar(profile = UserMock.profileRoomie1)
    }
}