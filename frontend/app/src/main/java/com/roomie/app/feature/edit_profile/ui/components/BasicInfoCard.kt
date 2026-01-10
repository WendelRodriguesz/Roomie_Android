package com.roomie.app.feature.edit_profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.components.LabeledOutlinedField
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.GenderOption
import com.roomie.app.feature.profile.model.UserMock
import com.roomie.app.feature.profile.model.UserProfile
import com.roomie.app.feature.profile.ui.components.ProfileAvatar

@Composable
fun BasicInfoCard(
    profile: UserProfile,
    name: String,
    onNameChange: (String) -> Unit,

    city: String,
    onCityChange: (String) -> Unit,

    course: String,
    onCourseChange: (String) -> Unit,

    bio: String,
    onBioChange: (String) -> Unit,

    gender: GenderOption?,
    onGenderChange: (GenderOption) -> Unit,

    isUploadingPhoto: Boolean,
    onPhotoChangeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedGender = gender ?: GenderOption.PREFIRO_NAO_INFORMAR

    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Informações básicas",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ProfileAvatar(profile)
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = onPhotoChangeClick,
                    enabled = !isUploadingPhoto
                ) {
                    Text(if (isUploadingPhoto) "Enviando..." else "Alterar foto")
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LabeledOutlinedField(
                    title = "Nome",
                    value = name,
                    onValueChange = onNameChange,
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

            }

            LabeledOutlinedField(
                title = "Curso / Profissão",
                value = course,
                onValueChange = onCourseChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            LabeledOutlinedField(
                title = "Cidade",
                value = city,
                onValueChange = onCityChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            LabeledOutlinedField(
                title = "Biografia",
                value = bio,
                onValueChange = { newBio -> if (newBio.length <= 230) onBioChange(newBio) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                minLines = 3,
                maxLines = 6,
                supportingText = { Text("${bio.length}/230", style = MaterialTheme.typography.bodySmall) }
            )

            HorizontalDivider()

            com.roomie.app.core.ui.components.ChipSelector(
                title = "Gênero",
                options = GenderOption.entries,
                selected = selectedGender,
                onSelect = onGenderChange,
                labelOf = { it.label }
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
            city = profile.city,
            onCityChange = {},
            course = profile.professionOrCourse ?: "",
            onCourseChange = {},
            bio = profile.bio,
            onBioChange = {},
            gender = profile.gender,
            onGenderChange = {},
            isUploadingPhoto = false,
            onPhotoChangeClick = {}
        )
    }
}

