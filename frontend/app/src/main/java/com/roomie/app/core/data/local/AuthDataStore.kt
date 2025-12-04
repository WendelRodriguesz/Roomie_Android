package com.roomie.app.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.roomie.app.core.data.model.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_data_store")

class AuthDataStore(private val context: Context) {
    companion object {
        private val USER_ID_KEY = longPreferencesKey("user_id")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val ROLE_KEY = stringPreferencesKey("role")
    }

    val userSession: Flow<UserSession?> = context.dataStore.data.map { preferences ->
        val id = preferences[USER_ID_KEY]
        val token = preferences[TOKEN_KEY]
        val refreshToken = preferences[REFRESH_TOKEN_KEY]
        val role = preferences[ROLE_KEY]

        if (id != null && token != null && refreshToken != null && role != null) {
            UserSession(id, token, refreshToken, role)
        } else {
            null
        }
    }

    suspend fun saveUserSession(session: UserSession) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = session.id
            preferences[TOKEN_KEY] = session.token
            preferences[REFRESH_TOKEN_KEY] = session.refreshToken
            preferences[ROLE_KEY] = session.role
        }
    }

    suspend fun clearUserSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
            preferences.remove(TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
            preferences.remove(ROLE_KEY)
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.map { it[TOKEN_KEY] }.firstOrNull()
    }
}

