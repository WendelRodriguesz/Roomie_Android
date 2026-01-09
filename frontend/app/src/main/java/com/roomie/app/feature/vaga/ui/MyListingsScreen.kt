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
        // Primeiro tenta buscar pelo perfil (caso o backend comece a retornar)
        try {
            val profileApiService = RetrofitClient.profileApiService
            val auth = "Bearer $token"
            android.util.Log.d("MyListingsScreen", "Buscando perfil do ofertante - userId: $userId")
            val profileResponse = profileApiService.getUsuarioOfertante(userId, auth)
            
            if (profileResponse.isSuccessful && profileResponse.body() != null) {
                val body = profileResponse.body()!!
                val anuncioIdFromProfile = body.anuncio?.id
                
                if (anuncioIdFromProfile != null) {
                    android.util.Log.d("MyListingsScreen", "✅ Anúncio encontrado no perfil! ID: $anuncioIdFromProfile")
                    // Limpa estados antes de setar o ID
                    error = null
                    isLoading = false
                    anuncioId = anuncioIdFromProfile
                    android.util.Log.d("MyListingsScreen", "Estado atualizado - anuncioId: $anuncioId, error: $error, isLoading: $isLoading")
                    return@LaunchedEffect
                } else {
                    android.util.Log.w("MyListingsScreen", "⚠️ Anúncio não encontrado no perfil (campo null)")
                    error = null // Limpa erro para tentar método alternativo
                }
            }
        } catch (e: Exception) {
            android.util.Log.w("MyListingsScreen", "Erro ao buscar via perfil", e)
        }
        
        // Como o perfil não retorna o anúncio, tenta usar o ID do usuário como ID do anúncio
        // Isso é uma solução temporária - o ideal é criar um endpoint no backend
        android.util.Log.d("MyListingsScreen", "Perfil não retornou anúncio. Tentando buscar anúncio com ID = userId: $userId")
        try {
            val result = anuncioRepository.visualizarAnuncio(userId.toLong(), token)
            
            result.fold(
                onSuccess = { anuncio ->
                    android.util.Log.d("MyListingsScreen", "✅ Anúncio encontrado! ID: ${anuncio.id}")
                    anuncioId = anuncio.id
                    isLoading = false
                },
                onFailure = { exception ->
                    android.util.Log.e("MyListingsScreen", "❌ Anúncio não encontrado com ID = userId", exception)
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
            // Prioridade: Se encontrou o anúncio, mostra a tela
            android.util.Log.d("MyListingsScreen", "🎨 Renderizando OfferorHomeRoute com anuncioId: $anuncioId")
            OfferorHomeRoute(
                anuncioId = anuncioId!!,
                token = token,
                refreshSignal = refreshSignal,
                onEditClick = { 
                    navController.navigate(Routes.EDIT_ANUNCIO.replace("{anuncioId}", anuncioId.toString()))
                },
                onError = { errorMsg ->
                    android.util.Log.e("MyListingsScreen", "❌ Erro do OfferorHomeRoute: $errorMsg")
                    // Não reseta o anuncioId imediatamente, apenas mostra o erro
                    // O usuário pode tentar recarregar
                    error = errorMsg
                }
            )
        }
        
        error != null -> {
            // Mostra erro apenas se não encontrou anúncio
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
