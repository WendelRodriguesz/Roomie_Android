package com.roomie.app.feature.profile.model

import androidx.annotation.DrawableRes

enum class ProfileRole { OFFEROR, SEEKER }
enum class PartyFrequency { NEVER, SOMETIMES, FREQUENT }
enum class SleepRoutine { MORNING, NIGHT, FLEXIBLE }

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
    val studySchedule: String,
    val tags: List<String>,
)

data class AccountSettings(
    val notificationsEnabled: Boolean,
    val showAsOnline: Boolean,
    val discoveryEnabled: Boolean,
    val darkModeEnabled: Boolean,
)

data class UserProfile(
    val id: String,
    val name: String,
    val age: Int,
    val role: ProfileRole,

    val city: String,
    val professionOrCourse: String?,
    val bio: String,

    val budget: Budget,
    val lifestyle: LifestylePreferences,
    val settings: AccountSettings,

    @DrawableRes val localPhoto: Int? = null,
    val photoUrl: String? = null,
)
