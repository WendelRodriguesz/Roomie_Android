package com.roomie.app.feature.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.roomie.app.R
import com.roomie.app.core.ui.components.GradientButton
import com.roomie.app.core.ui.components.RoomieTextField
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.navigation.Routes

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
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
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Text(
                    text = "Lembre-se de mim",
                    fontSize = 12.sp
                )
            }

            Text(
                text = "Esqueceu sua senha?",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {  }
            )
        }

        Spacer(Modifier.height(28.dp))

        GradientButton(
            text = "Login",
            buttonTextSize = 18,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable { navController.navigate(Routes.HOME)},
            onClick = {}
        )

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