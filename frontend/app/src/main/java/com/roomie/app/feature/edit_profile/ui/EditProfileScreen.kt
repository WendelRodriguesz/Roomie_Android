package com.roomie.app.feature.edit_profile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.components.BudgetCard
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.*
import com.roomie.app.feature.edit_profile.ui.components.BasicInfoCard
import com.roomie.app.feature.edit_profile.ui.components.LifestylePreferencesCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    profile: UserProfile,
    isSaving: Boolean = false,
    onCancelClick: () -> Unit = {},
    onSaveClick: (UserProfile) -> Unit = {},
) {
    var name by remember(profile.id) { mutableStateOf(profile.name) }
    var gender by remember(profile.id) { mutableStateOf(profile.gender ?: GenderOption.PREFIRO_NAO_INFORMAR) }
    var city by remember(profile.id) { mutableStateOf(profile.city) }
    var course by remember(profile.id) { mutableStateOf(profile.professionOrCourse ?: "") }
    var bio by remember(profile.id) { mutableStateOf(profile.bio) }

    var minBudget by remember { mutableIntStateOf(profile.budget.minBudget ?: 800) }
    var maxBudget by remember { mutableIntStateOf(profile.budget.maxBudget ?: 1200) }

    var cleanliness by remember { mutableIntStateOf(profile.lifestyle.cleanlinessLevel) }
    var socialLevel by remember { mutableIntStateOf(profile.lifestyle.socialLevel) }

    var isSmoker by remember { mutableStateOf(profile.lifestyle.isSmoker) }
    var acceptsPets by remember { mutableStateOf(profile.lifestyle.acceptsPets) }
    var sleepRoutine by remember { mutableStateOf(profile.lifestyle.sleepRoutine) }
    var partyFreq by remember { mutableStateOf(profile.lifestyle.partyFrequency) }
    var studySchedule by remember { mutableStateOf(profile.lifestyle.studySchedule ?: "") }
    var acceptsSharedRoom by remember { mutableStateOf(profile.lifestyle.acceptsSharedRoom) }


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
                                budget = Budget(minBudget, maxBudget),
                                lifestyle = profile.lifestyle.copy(
                                    cleanlinessLevel = cleanliness,
                                    socialLevel = socialLevel,
                                    isSmoker = isSmoker,
                                    acceptsPets = acceptsPets,
                                    partyFrequency = partyFreq,
                                    sleepRoutine = sleepRoutine,
                                    studySchedule = studySchedule.ifBlank { null },
                                    acceptsSharedRoom = acceptsSharedRoom,
                                )
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
                onPhotoChangeClick = { /* Fluxo de trocar foto, implementar depois */ }
            )

            BudgetCard(
                minBudget = minBudget,
                onMinBudgetChange = { minBudget = it },
                maxBudget = maxBudget,
                onMaxBudgetChange = { maxBudget = it }
            )

            LifestylePreferencesCard(
                acceptsPets = acceptsPets,
                onAcceptsPetsChange = { acceptsPets = it },
                isSmoker = isSmoker,
                onIsSmokerChange = { isSmoker = it },
                acceptsSharedRoom = acceptsSharedRoom,
                onAcceptsSharedRoomChange = { acceptsSharedRoom = it },
                sleepRoutine = sleepRoutine,
                onSleepRoutineChange = { sleepRoutine = it },
                partyFrequency = partyFreq,
                onPartyFrequencyChange = { partyFreq = it },
            )
        }
    }
}

@RoomiePreview
@Composable
private fun EditProfilePreview(){
    Roomie_AndroidTheme (dynamicColor = false){
        EditProfileScreen(UserMock.profileYou)
    }
}