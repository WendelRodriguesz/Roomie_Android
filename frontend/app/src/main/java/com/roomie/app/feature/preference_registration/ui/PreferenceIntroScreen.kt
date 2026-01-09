package com.roomie.app.feature.preference_registration.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.core.ui.components.GradientButton
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.RoomieTheme
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.navigation.Routes

@Composable
fun PreferenceIntroScreen(navController: NavController) {
    val role = AuthSession.role

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
            text = "Vamos personalizar sua experiência",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Preencha algumas informações sobre você para encontrarmos opções mais compatíveis.\n\nVocê pode pular esta etapa e completar depois, se preferir.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )

        Spacer(Modifier.height(40.dp))

        GradientButton(
            text = "Vamos lá!",
            buttonTextSize = 18,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                navController.navigate(Routes.PREFERENCES_REGISTRATION) {
                    popUpTo(Routes.PREFERENCE_INTRO) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )

        Spacer(Modifier.height(16.dp))

        TextButton(
            onClick = {
                val targetRoute = if (role == ProfileRole.OFFEROR) {
                    Routes.MY_LISTINGS
                } else {
                    Routes.HOME
                }
                navController.navigate(targetRoute) {
                    popUpTo(Routes.PREFERENCE_INTRO) { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Pular por enquanto",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@RoomiePreview
@Composable
private fun PreferenceIntroScreenPreview() {
    AuthSession.role = ProfileRole.SEEKER

    Roomie_AndroidTheme(dynamicColor = false) {
        Surface {
            PreferenceIntroScreen(navController = androidx.navigation.compose.rememberNavController())
        }
    }
}