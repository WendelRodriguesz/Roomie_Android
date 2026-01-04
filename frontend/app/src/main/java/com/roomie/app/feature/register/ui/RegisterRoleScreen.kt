package com.roomie.app.feature.register.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.roomie.app.feature.profile.model.ProfileRole
import com.roomie.app.navigation.Routes
import com.roomie.app.feature.register.components.ObjectiveButton

@Composable
fun RegisterRoleScreen(
    navController: NavController, viewModel: RegisterViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.clearError()
    }

    LaunchedEffect(uiState.isRegistrationSuccessful) {
        if (uiState.isRegistrationSuccessful) {
            snackbarHostState.showSnackbar("Cadastro realizado com sucesso! Você será direcionado para a tela de login em instantes.")
            navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.REGISTER) {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            snackbarHostState.showSnackbar(
                "Algo deu errado. Tente novamente!"
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Qual é o seu objetivo?",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                ObjectiveButton(
                    background = Color(0xFF0078FF),
                    text = "Tenho um lugar e busco colega de quarto",
                    buttonTextSize = 15,
                    onClick = {
                        viewModel.clearError()
                        viewModel.completeRegistration(ProfileRole.OFFEROR)
                    })

                Spacer(modifier = Modifier.height(16.dp))

                ObjectiveButton(
                    background = Color(0xFFFF4E88),
                    text = "Estou procurando um lugar pra morar",
                    buttonTextSize = 15,
                    onClick = {
                        viewModel.completeRegistration(ProfileRole.SEEKER)
                    })
            }
        }

        SnackbarHost(
            hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

//@RoomiePreview
//@Composable
//private fun RegisterRoleScreenPreview() {
//    Roomie_AndroidTheme(dynamicColor = false) {
//        Surface {
//            val navController = rememberNavController()
//            RegisterRoleScreen(navController)
//        }
//    }
//}