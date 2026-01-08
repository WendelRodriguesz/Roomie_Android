package com.roomie.app.feature.edit_profile.ui

import androidx.compose.runtime.Composable
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.preference_registration.ui.PreferenceRegistration

@Composable
fun EditPreferencesRoute(
    userId: Long,
    token: String,
    role: ProfileRole,
    onCancel: () -> Unit,
    onSaved: () -> Unit,
) {
    PreferenceRegistration(
        role = role,
        onSkipClick = onCancel,
        onSaveClick = { _ -> onSaved() }
    )
}
