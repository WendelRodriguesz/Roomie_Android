package com.roomie.app.feature.vaga.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.core.data.session.AuthSession
import com.roomie.app.feature.offeror_home.ui.OfferorHomeRoute
import com.roomie.app.feature.offeror_home.data.AnuncioRepository
import com.roomie.app.navigation.Routes

@Composable
fun MyListingsScreen(
    navController: NavController,
    refreshSignal: Long = 0L,
    onCreateListingClick: () -> Unit
) {
    val userId = AuthSession.userId
    val token = AuthSession.token

    if (userId == null || token.isNullOrBlank()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Usuário não autenticado",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        return
    }

    val anuncioRepository = remember { AnuncioRepository(RetrofitClient.anuncioApiService) }
    var anuncioId by remember { mutableStateOf<Long?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(userId, token) {
        try {
            val profileApiService = RetrofitClient.profileApiService
            val auth = "Bearer $token"
            val profileResponse = profileApiService.getUsuarioOfertante(userId, auth)
            
            if (profileResponse.isSuccessful && profileResponse.body() != null) {
                val body = profileResponse.body()!!
                val anuncioIdFromProfile = body.anuncio?.id
                
                if (anuncioIdFromProfile != null) {
                    error = null
                    isLoading = false
                    anuncioId = anuncioIdFromProfile
                    return@LaunchedEffect
                } else {
                    error = null
                }
            }
        } catch (e: Exception) {
        }
        
        try {
            val result = anuncioRepository.visualizarAnuncio(userId.toLong(), token)
            
            result.fold(
                onSuccess = { anuncio ->
                    anuncioId = anuncio.id
                    isLoading = false
                },
                onFailure = { exception ->
                    android.util.Log.e("MyListingsScreen", "Erro ao buscar anúncio", exception)
                    isLoading = false
                    error = "Não foi possível encontrar seu anúncio. Por favor, certifique-se de que você possui um anúncio cadastrado ou cadastre uma vaga primeiro."
                }
            )
        } catch (e: Exception) {
            android.util.Log.e("MyListingsScreen", "Exceção ao buscar anúncio", e)
            isLoading = false
            error = "Erro ao buscar anúncio: ${e.message}"
        }
    }

    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        
        anuncioId != null -> {
            OfferorHomeRoute(
                anuncioId = anuncioId!!,
                token = token,
                refreshSignal = refreshSignal,
                onEditClick = { 
                    navController.navigate(Routes.EDIT_ANUNCIO.replace("{anuncioId}", anuncioId.toString()))
                },
                onError = { errorMsg ->
                    error = errorMsg
                }
            )
        }
        
        error != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = error ?: "Erro desconhecido",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onCreateListingClick) {
                    Text("Cadastrar Vaga")
                }
            }
        }
    }
}
