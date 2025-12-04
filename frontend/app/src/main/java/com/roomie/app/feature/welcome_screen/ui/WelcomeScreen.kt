package com.roomie.app.feature.welcome_screen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.RoomieTheme
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.navigation.Routes

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(RoomieTheme.assets.logoRes),
                contentDescription = "Logo Roomie",
                modifier = Modifier
                    .size(200.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Bem-vindo!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Encontre seu lar perfeito\ne pessoas incríveis para dividir",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }

        GradientButton(
            text = "Vamos começar!",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            buttonTextSize = 20,
            onClick = {navController.navigate(Routes.LOGIN)}
        )
    }
}

@RoomiePreview
@Composable
private fun WelcomeScreenPreview(){
    Roomie_AndroidTheme(dynamicColor = false) {
        Surface {
            val navController = rememberNavController()
            WelcomeScreen(navController)
        }
    }
}
