package com.roomie.app.feature.login.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roomie.app.core.data.local.AuthDataStore
import com.roomie.app.feature.login.data.AuthRepository

class LoginViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val authDataStore = AuthDataStore(context)
            val authRepository = AuthRepository(authDataStore)
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

