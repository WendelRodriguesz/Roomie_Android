package com.roomie.app.feature.edit_profile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.UserMock
import com.roomie.app.feature.profile.model.UserProfile
import com.roomie.app.feature.profile.ui.components.ProfileAvatar

@Composable
fun BasicInfoCard(
    profile: UserProfile,
    name: String,
    onNameChange: (String) -> Unit,
    age: String,
    onAgeChange: (String) -> Unit,
    city: String,
    onCityChange: (String) -> Unit,
    course: String,
    onCourseChange: (String) -> Unit,
    bio: String,
    onBioChange: (String) -> Unit,
    onPhotoChangeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Informações básicas",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ProfileAvatar(profile)
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onPhotoChangeClick) {
                    Text("Alterar foto")
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    modifier = Modifier.weight(1f),
                    label = { Text("Nome") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = age,
                    onValueChange = { newAge ->
                        if (newAge.all { char -> char.isDigit() } && newAge.length <= 3) {
                            onAgeChange(newAge)
                        }
                    },
                    modifier = Modifier.width(100.dp),
                    label = { Text("Idade") },
                    singleLine = true
                )
            }

            OutlinedTextField(
                value = course,
                onValueChange = onCourseChange,
                label = { Text("Curso / Profissão") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = city,
                onValueChange = onCityChange,
                label = { Text("Cidade") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = bio,
                onValueChange = { newBio ->
                    if (newBio.length <= 230) {
                        onBioChange(newBio)
                    }
                },
                label = { Text("Biografia") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 6,
                supportingText = {
                    Text(
                        text = "${bio.length}/230",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )
        }
    }
}

@RoomiePreview
@Composable
private fun BasicInfoCardPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        val profile = UserMock.profileYou
        BasicInfoCard(
            profile = profile,
            name = profile.name,
            onNameChange = {},
            age = profile.age.toString(),
            onAgeChange = {},
            city = profile.city,
            onCityChange = {},
            course = profile.professionOrCourse ?: "",
            onCourseChange = {},
            bio = profile.bio,
            onBioChange = {},
            onPhotoChangeClick = {}
        )
    }
}

