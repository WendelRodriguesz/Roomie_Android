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
import com.roomie.app.core.ui.components.ChipSelector
import com.roomie.app.feature.preference_registration.components.PreferenceRegistrationLifestyleCard
import com.roomie.app.feature.preference_registration.model.UserPreferences
import com.roomie.app.feature.profile.model.Budget
import com.roomie.app.feature.profile.model.CleaningHabit
import com.roomie.app.feature.profile.model.PartyFrequency
import com.roomie.app.feature.profile.model.SleepRoutine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceRegistration(
    initialPreferences: UserPreferences = UserPreferences(),
    isSaving: Boolean = false,
    onSaveClick: (UserPreferences) -> Unit = {},
    onSkipClick: () -> Unit = {},
) {
    var partyFrequency by remember { mutableStateOf(initialPreferences.partyFrequency) }
    var cleaningHabit by remember { mutableStateOf(initialPreferences.cleaningHabit) }
    var acceptsPets by remember { mutableStateOf(initialPreferences.acceptsPets) }
    var sleepRoutine by remember { mutableStateOf(initialPreferences.sleepRoutine) }
    var minBudget by remember { mutableIntStateOf(initialPreferences.budget.minBudget ?: 800) }
    var maxBudget by remember { mutableIntStateOf(initialPreferences.budget.maxBudget ?: 1200) }
    var acceptsRoomSharing by remember { mutableStateOf(initialPreferences.acceptsRoomSharing) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Preferências") },
                actions = {
                    TextButton(onClick = onSkipClick) { Text("Pular") }

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

                            onSaveClick(
                                UserPreferences(
                                    partyFrequency = partyFrequency,
                                    cleaningHabit = cleaningHabit,
                                    acceptsPets = acceptsPets,
                                    sleepRoutine = sleepRoutine,
                                    budget = Budget(finalMin, finalMax),
                                    acceptsRoomSharing = acceptsRoomSharing,
                                )
                            )
                        }
                    ) { Text(if (isSaving) "Salvando..." else "Salvar") }
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


            BudgetCard(
                minBudget = minBudget,
                onMinBudgetChange = { minBudget = it },
                maxBudget = maxBudget,
                onMaxBudgetChange = { maxBudget = it }
            )
        }
    }
}

@RoomiePreview
@Composable
private fun PreferenceRegistrationPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        PreferenceRegistration()
    }
}
