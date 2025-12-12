package com.roomie.app.feature.profile.model

import androidx.annotation.DrawableRes

enum class ProfileRole { OFFEROR, SEEKER }

enum class PartyFrequency { NEVER, SOMETIMES, FREQUENT }

enum class SleepRoutine { MORNING, NIGHT, FLEXIBLE }

enum class CleaningHabit { DAILY, WEEKLY, BIWEEKLY, OCCASIONAL }

enum class GenderOption(val label: String) {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    NAO_BINARIO("Não-binário"),
    PREFIRO_NAO_DIZER("Prefiro não dizer")
}

data class Budget(
    val minBudget: Int?,
    val maxBudget: Int?,
)

data class LifestylePreferences(
    val acceptsPets: Boolean,
    val isSmoker: Boolean,
    val partyFrequency: PartyFrequency,
    val isQuiet: Boolean,
    val sleepRoutine: SleepRoutine,
    val cleanlinessLevel: Int,
    val socialLevel: Int,
    val studySchedule: String?,
    val cleaningHabit: CleaningHabit,
    val acceptsSharedRoom: Boolean,
    val tags: List<String>,
)

data class AccountSettings(
    val notificationsEnabled: Boolean,
    val showAsOnline: Boolean,
    val discoveryEnabled: Boolean,
    val darkModeEnabled: Boolean,
)

data class UserProfile(
    val id: Long,
    val name: String,
    val email: String?,
    val age: Int?,
    val role: ProfileRole,

    val city: String,
    val professionOrCourse: String?,
    val bio: String,

    val gender: GenderOption?,

    val budget: Budget,
    val lifestyle: LifestylePreferences,
    val settings: AccountSettings,

    @DrawableRes val localPhoto: Int? = null,
    val photoUrl: String? = null,
)
