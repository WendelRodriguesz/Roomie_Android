package com.roomie.app.feature.preference_registration.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.feature.preference_registration.model.UserPreferences
import com.roomie.app.feature.profile.data.ProfileRepository
import com.roomie.app.feature.profile.data.remote.dto.UsuarioInteressadoDto
import com.roomie.app.feature.profile.data.remote.dto.UsuarioOfertanteDto
import com.roomie.app.feature.profile.model.toUserPreferences
import com.roomie.app.navigation.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PreferenceRegistrationRoute(
    navController: NavController,
    role: ProfileRole,
    userId: Long,
    token: String
) {
    var initialPreferences by remember { mutableStateOf<UserPreferences?>(null) }
    var isLoadingPreferences by remember { mutableStateOf(true) }
    var isSaving by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(role, userId, token) {
        withContext(Dispatchers.IO) {
            try {
                val auth = "Bearer $token"
                val api = RetrofitClient.profileApiService

                val prefs = if (role == ProfileRole.SEEKER) {
                    val response = api.getUsuarioInteressado(userId, auth)

                    if(response.isSuccessful && response.body() != null) {
                        val dto: UsuarioInteressadoDto = response.body()!!
                        dto.interesses?.toUserPreferences()
                    } else {
                        null
                    }

                } else {
                    val response = api.getUsuarioOfertante(userId, auth)

                    if(response.isSuccessful && response.body() != null) {
                        val dto: UsuarioOfertanteDto = response.body()!!
                        dto.interesses?.toUserPreferences()
                    } else {
                        null
                    }
                }

                withContext(Dispatchers.Main) {
                    initialPreferences = prefs ?: UserPreferences()
                    isLoadingPreferences = false
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    initialPreferences = UserPreferences()
                    isLoadingPreferences = false
                }
            }
        }
    }

    if(isLoadingPreferences) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    PreferenceRegistration(
        role = role,
        initialPreferences = initialPreferences ?: UserPreferences(),
        isSaving = isSaving,
        onSaveClick = { prefs ->
            isSaving = true
            val repository = ProfileRepository(RetrofitClient.profileApiService)

            scope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        val auth = "Bearer $token"
                        val api = RetrofitClient.profileApiService

                        val (hasExisting, interessesId) = if (role == ProfileRole.SEEKER) {
                            val response = api.getUsuarioInteressado(userId, auth)

                            if (response.isSuccessful && response.body() != null) {
                                val dto = response.body()!!
                                val exists = dto.interesses != null
                                val id = dto.interesses?.id ?: userId
                                Pair(exists, id)
                            } else {
                                Pair(false, userId)
                            }
                        } else {
                            val response = api.getUsuarioOfertante(userId, auth)

                            if (response.isSuccessful && response.body() != null) {
                                val dto = response.body()!!
                                val exists = dto.interesses != null

                                val id = dto.interesses?.id ?: userId
                                Pair(exists, id)
                            } else {
                                Pair(false, userId)
                            }
                        }

                        val result = repository.upsertPreferences(
                            role = role,
                            interessesId = interessesId,
                            token = token,
                            hasExisting = hasExisting,
                            prefs = prefs
                        )

                        result.fold(
                            onSuccess = {
                                withContext(Dispatchers.Main) {
                                    isSaving = false
                                    val targetRoute = if (role == ProfileRole.OFFEROR) {
                                        Routes.MY_LISTINGS
                                    } else {
                                        Routes.HOME
                                    }

                                    navController.navigate(targetRoute) {
                                        popUpTo(Routes.PREFERENCES_REGISTRATION) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                            },
                            onFailure = { exception ->
                                scope.launch {
                                    snackbarHostState.showSnackbar("Erro ao salvar preferências. Tente novamente.")
                                }
                                withContext(Dispatchers.Main) {
                                    isSaving = false
                                }
                            }
                        )
                    } catch (e: Exception) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Erro ao salvar preferências. Tente novamente.")
                        }
                        withContext(Dispatchers.Main) {
                            isSaving = false
                        }
                    }
                }
            }

        },
        onSkipClick = {
            val targetRoute = if (role == ProfileRole.OFFEROR) {
                Routes.MY_LISTINGS
            } else {
                Routes.HOME
            }
            navController.navigate(targetRoute) {
                popUpTo(Routes.PREFERENCES_REGISTRATION) { inclusive = true }
                launchSingleTop = true
            }
        },
        skipText = "Pular",
        snackbarHostState = snackbarHostState
    )
}