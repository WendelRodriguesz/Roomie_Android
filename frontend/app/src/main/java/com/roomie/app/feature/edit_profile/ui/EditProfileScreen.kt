@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.roomie.app.feature.edit_profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.edit_profile.ui.components.BasicInfoCard
import com.roomie.app.feature.profile.model.GenderOption
import com.roomie.app.feature.profile.model.UserMock
import com.roomie.app.feature.profile.model.UserProfile

@Composable
fun EditProfileScreen(
    profile: UserProfile,
    isSaving: Boolean = false,
    isUploadingPhoto: Boolean = false,
    onCancelClick: () -> Unit = {},
    onSaveClick: (UserProfile) -> Unit = {},
    onPhotoChangeClick: () -> Unit = {},
) {
    var name by remember(profile.id) { mutableStateOf(profile.name) }
    var gender by remember(profile.id) { mutableStateOf(profile.gender ?: GenderOption.PREFIRO_NAO_INFORMAR) }
    var city by remember(profile.id) { mutableStateOf(profile.city) }
    var course by remember(profile.id) { mutableStateOf(profile.professionOrCourse ?: "") }
    var bio by remember(profile.id) { mutableStateOf(profile.bio) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Perfil") },
                actions = {
                    TextButton(onClick = onCancelClick) {
                        Text("Cancelar")
                    }

                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        enabled = !isSaving,
                        onClick = {
                            val updated = profile.copy(
                                name = name,
                                gender = gender,
                                city = city,
                                professionOrCourse = course.ifBlank { null },
                                bio = bio,
                            )
                            onSaveClick(updated)
                        }
                    ) {
                        Text(if (isSaving) "Salvando..." else "Salvar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
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
            BasicInfoCard(
                profile = profile,
                name = name,
                onNameChange = { name = it },
                gender = gender,
                onGenderChange = { gender = it },
                city = city,
                onCityChange = { city = it },
                course = course,
                onCourseChange = { course = it },
                bio = bio,
                onBioChange = { bio = it },
                isUploadingPhoto = isUploadingPhoto,
                onPhotoChangeClick = onPhotoChangeClick
            )
        }
    }
}

@RoomiePreview
@Composable
private fun EditProfilePreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        EditProfileScreen(UserMock.profileYou)
    }
}
