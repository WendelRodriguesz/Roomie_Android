package com.roomie.app.feature.preference_registration.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.components.BudgetCard
import com.roomie.app.feature.preference_registration.components.SectionCard
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.preference_registration.components.PreferenceRegistrationLifestyleCard
import com.roomie.app.feature.preference_registration.model.UserPreferences
import com.roomie.app.feature.profile.model.Budget
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.edit_profile.ui.components.PreferenceSwitch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceRegistration(
    role: ProfileRole,
    initialPreferences: UserPreferences = UserPreferences(),
    isSaving: Boolean = false,
    onSaveClick: (UserPreferences) -> Unit = {},
    onSkipClick: () -> Unit = {},
    skipText: String = "Pular",
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    var partyFrequency by remember(initialPreferences) { mutableStateOf(initialPreferences.partyFrequency) }
    var cleaningHabit by remember(initialPreferences) { mutableStateOf(initialPreferences.cleaningHabit) }
    var acceptsPets by remember(initialPreferences) { mutableStateOf(initialPreferences.acceptsPets) }
    var sleepRoutine by remember(initialPreferences) { mutableStateOf(initialPreferences.sleepRoutine) }
    var acceptsRoomSharing by remember(initialPreferences) { mutableStateOf(initialPreferences.acceptsRoomSharing) }

    var minBudget by remember(initialPreferences) { mutableIntStateOf(initialPreferences.budget.minBudget ?: 800) }
    var maxBudget by remember(initialPreferences) { mutableIntStateOf(initialPreferences.budget.maxBudget ?: 1200) }

    var isSmoker by remember(initialPreferences) { mutableStateOf(initialPreferences.isSmoker) }
    var drinksAlcohol by remember(initialPreferences) { mutableStateOf(initialPreferences.drinksAlcohol) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Preferências") },
                actions = {
                    TextButton(onClick = onSkipClick) { Text(skipText) }
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        enabled = !isSaving,
                        onClick = {
                            var finalMin = minBudget
                            var finalMax = maxBudget
                            if (finalMin > finalMax) {
                                val tmp = finalMin
                                finalMin = finalMax
                                finalMax = tmp
                            }

                            val prefs = UserPreferences(
                                partyFrequency = partyFrequency,
                                cleaningHabit = cleaningHabit,
                                acceptsPets = acceptsPets,
                                sleepRoutine = sleepRoutine,
                                budget = Budget(finalMin, finalMax),
                                acceptsRoomSharing = acceptsRoomSharing,
                                isSmoker = isSmoker,
                                drinksAlcohol = drinksAlcohol,
                            )

                            onSaveClick(prefs)
                        }
                    ) { Text(if (isSaving) "Salvando..." else "Salvar") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PreferenceRegistrationLifestyleCard(
                acceptsPets = acceptsPets,
                onAcceptsPetsChange = { acceptsPets = it },

                acceptsSharedRoom = acceptsRoomSharing,
                onAcceptsSharedRoomChange = { acceptsRoomSharing = it },

                sleepRoutine = sleepRoutine,
                onSleepRoutineChange = { sleepRoutine = it },

                partyFrequency = partyFrequency,
                onPartyFrequencyChange = { partyFrequency = it },

                cleaningHabit = cleaningHabit,
                onCleaningHabitChange = { cleaningHabit = it },
            )

            // Só Interessado
            if (role == ProfileRole.SEEKER) {
                SectionCard(title = "Hábitos") {
                    PreferenceSwitch(
                        label = "Fumante",
                        checked = isSmoker,
                        onCheckedChange = { isSmoker = it }
                    )
                    PreferenceSwitch(
                        label = "Consome bebidas alcoólicas",
                        checked = drinksAlcohol,
                        onCheckedChange = { drinksAlcohol = it }
                    )
                }

                BudgetCard(
                    minBudget = minBudget,
                    onMinBudgetChange = { minBudget = it },
                    maxBudget = maxBudget,
                    onMaxBudgetChange = { maxBudget = it }
                )
            }
        }
    }
}

@RoomiePreview
@Composable
private fun PreferenceRegistrationPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        PreferenceRegistration(ProfileRole.SEEKER)
    }
}
