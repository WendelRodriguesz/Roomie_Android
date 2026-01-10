package com.roomie.app.feature.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.core.ui.components.GradientButton
import com.roomie.app.core.ui.components.RoomieTextField
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.RoomieTheme
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.login.presentation.LoginViewModel
import com.roomie.app.feature.login.presentation.LoginViewModelFactory
import com.roomie.app.navigation.Routes

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(context)
    )
    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    LaunchedEffect(uiState.isLoginSuccessful, uiState.needsPreferencesSetup) {
        if (uiState.isLoginSuccessful) {
            if (uiState.needsPreferencesSetup) {
                navController.navigate(Routes.PREFERENCE_INTRO) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                    launchSingleTop = true
                }
            } else {
                val role = AuthSession.role
                val targetRoute = if (role == ProfileRole.OFFEROR) {
                    Routes.MY_LISTINGS
                } else {
                    Routes.HOME
                }

                navController.navigate(targetRoute) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    LaunchedEffect(email, senha) {
        kotlinx.coroutines.delay(300)
        if (uiState.errorMessage != null && (email.isNotEmpty() || senha.isNotEmpty())) {
            viewModel.clearError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(RoomieTheme.assets.logoRes),
            contentDescription = "Roomie Logo",
            modifier = Modifier.size(160.dp)
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Login",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "faça login para acessar sua conta",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(28.dp))

        RoomieTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "e-mail",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        RoomieTextField(
            value = senha,
            onValueChange = { senha = it },
            placeholder = "senha",
            modifier = Modifier.fillMaxWidth(),
            isPassword = true
        )

        Spacer(Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Esqueceu sua senha?",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {  }
            )
        }

        Spacer(Modifier.height(28.dp))

        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
        }

        GradientButton(
            text = if (uiState.isLoading) "Entrando..." else "Login",
            buttonTextSize = 18,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                if (!uiState.isLoading) {
                    viewModel.login(email, senha)
                }
            }
        )

        if (uiState.isLoading) {
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(10.dp))

        Row {
            Text(
                text = "Novo usuário? ",
                fontSize = 13.sp
            )
            Text(
                text = "Cadastre-se",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier.clickable { navController.navigate(Routes.REGISTER) }
            )
        }
    }
}

@RoomiePreview
@Composable
private fun LoginScreenPreview(){
    Roomie_AndroidTheme (dynamicColor = false){
        Surface {
            val navController = rememberNavController()
            LoginScreen(navController)
        }
    }
}