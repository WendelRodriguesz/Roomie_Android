package com.roomie.app.feature.register.ui

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.roomie.app.core.ui.components.GradientButton
import com.roomie.app.core.ui.components.RoomieTextField
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Pink
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.navigation.Routes

@Composable
fun RegisterScreen(navController: NavController) {

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("")}
    var dataDeNascimento by remember { mutableStateOf("")}
    var senha by remember { mutableStateOf("")}
    var termosDeUso by remember { mutableStateOf(false)}

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Text(
            text = "Cadastre-se",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Encontre o colega de quarto perfeito para você!",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(28.dp))

        RoomieTextField(
            value = nome,
            onValueChange = {nome = it},
            placeholder = "nome",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        RoomieTextField(
            value = email,
            onValueChange = {email = it},
            placeholder = "email",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        RoomieTextField(
            value = dataDeNascimento,
            onValueChange = {dataDeNascimento = it},
            placeholder = "data de nascimento",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        RoomieTextField(
            value = senha,
            onValueChange = {senha = it},
            placeholder = "senha",
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically,) {
            Checkbox(
                checked = termosDeUso,
                onCheckedChange = {termosDeUso = it},
            )
            Text (
                text = "Ao marcar essa caixa você aceita os Termos e Condições",
                fontSize = 11.sp
            )
        }

        Spacer(Modifier.height(28.dp))

        GradientButton(
            text = "Continuar",
            buttonTextSize = 18,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = { navController.navigate(Routes.REGISTER_ROLE) }
        )

        Spacer(Modifier.height(10.dp))

        Row {
            Text(
                text = "Já tem uma conta? ",
                fontSize = 13.sp
            )
            Text(
                text = "Faça Login",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier.clickable { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}

@RoomiePreview
@Composable
private fun RegisterScreenPreview(){
    Roomie_AndroidTheme (dynamicColor = false){
        Surface {
            val navController = rememberNavController()
            RegisterScreen(navController)
        }
    }
}