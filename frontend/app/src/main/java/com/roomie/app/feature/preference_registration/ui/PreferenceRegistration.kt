package com.roomie.app.feature.preference_registration.ui

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
import com.roomie.app.feature.preference_registration.components.*
import com.roomie.app.feature.preference_registration.model.UserPreferences
import com.roomie.app.feature.profile.model.Budget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceRegistration(
    initialPreferences: UserPreferences = UserPreferences(),
    onSaveClick: (UserPreferences) -> Unit = {},
    onSkipClick: () -> Unit = {}
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
                    TextButton(onClick = onSkipClick) {
                        Text("Pular")
                    }
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            val preferences = UserPreferences(
                                partyFrequency = partyFrequency,
                                cleaningHabit = cleaningHabit,
                                acceptsPets = acceptsPets,
                                sleepRoutine = sleepRoutine,
                                budget = Budget(minBudget, maxBudget),
                                acceptsRoomSharing = acceptsRoomSharing
                            )
                            onSaveClick(preferences)
                        }
                    ) {
                        Text("Salvar")
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
            PartyFrequencyCard(
                partyFrequency = partyFrequency,
                onPartyFrequencyChange = { partyFrequency = it }
            )

            CleaningHabitCard(
                cleaningHabit = cleaningHabit,
                onCleaningHabitChange = { cleaningHabit = it }
            )

            LifestyleOptionsCard(
                acceptsPets = acceptsPets,
                onAcceptsPetsChange = { acceptsPets = it },
                acceptsRoomSharing = acceptsRoomSharing,
                onAcceptsRoomSharingChange = { acceptsRoomSharing = it }
            )

            SleepRoutineCard(
                sleepRoutine = sleepRoutine,
                onSleepRoutineChange = { sleepRoutine = it }
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
private fun PreferenceRegistrationPreview(){
    Roomie_AndroidTheme (dynamicColor = false){
        PreferenceRegistration()
    }
}