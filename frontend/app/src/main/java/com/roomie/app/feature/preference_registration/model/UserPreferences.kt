package com.roomie.app.feature.preference_registration.model

import com.roomie.app.feature.profile.model.Budget
import com.roomie.app.feature.profile.model.CleaningHabit
import com.roomie.app.feature.profile.model.PartyFrequency
import com.roomie.app.feature.profile.model.SleepRoutine
data class UserPreferences(
    val occupation: String = "",
    val bio: String = "",
    val partyFrequency: PartyFrequency = PartyFrequency.SOMETIMES,
    val cleaningHabit: CleaningHabit = CleaningHabit.WEEKLY,
    val acceptsPets: Boolean = false,
    val sleepRoutine: SleepRoutine = SleepRoutine.FLEXIBLE,
    val budget: Budget = Budget(minBudget = 800, maxBudget = 1200),
    val acceptsRoomSharing: Boolean = false
)

