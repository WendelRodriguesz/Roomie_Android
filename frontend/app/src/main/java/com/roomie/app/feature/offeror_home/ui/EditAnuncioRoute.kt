package com.roomie.app.feature.offeror_home.ui

import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.feature.offeror_home.data.AnuncioRepository
import com.roomie.app.feature.offeror_home.presentation.EditAnuncioEvent
import com.roomie.app.feature.offeror_home.presentation.EditAnuncioViewModel
import com.roomie.app.feature.offeror_home.presentation.EditAnuncioViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun EditAnuncioRoute(
    anuncioId: Long,
    token: String,
    onCancel: () -> Unit,
    onSaved: () -> Unit
) {
    val repository = remember { AnuncioRepository(RetrofitClient.anuncioApiService) }
    val userId = com.roomie.app.core.data.session.AuthSession.userId ?: 0L
    val viewModel: EditAnuncioViewModel = viewModel(
        factory = EditAnuncioViewModelFactory(repository, anuncioId, userId, token)
    )

    val state by viewModel.state.collectAsStateWithLifecycle()
    val titulo by viewModel.titulo.collectAsStateWithLifecycle()
    val descricao by viewModel.descricao.collectAsStateWithLifecycle()
    val rua by viewModel.rua.collectAsStateWithLifecycle()
    val numero by viewModel.numero.collectAsStateWithLifecycle()
    val bairro by viewModel.bairro.collectAsStateWithLifecycle()
    val cidade by viewModel.cidade.collectAsStateWithLifecycle()
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val valorAluguel by viewModel.valorAluguel.collectAsStateWithLifecycle()
    val valorContas by viewModel.valorContas.collectAsStateWithLifecycle()
    val vagasDisponiveis by viewModel.vagasDisponiveis.collectAsStateWithLifecycle()
    val tipoImovel by viewModel.tipoImovel.collectAsStateWithLifecycle()
    val comodos by viewModel.comodos.collectAsStateWithLifecycle()
    val fotos = state.anuncio?.fotos ?: emptyList()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    fun queryFileName(uri: Uri): String {
        val cr = context.contentResolver
        val cursor: Cursor? = cr.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
        if (cursor != null) {
            cursor.use {
                val idx = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (it.moveToFirst() && idx >= 0) {
                    val value = it.getString(idx)
                    if (!value.isNullOrBlank()) return value
                }
            }
        }
        return "foto.jpg"
    }

    val pickPhoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult

        scope.launch {
            val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            if (bytes == null || bytes.isEmpty()) {
                // O erro será mostrado pelo snackbar da tela através do errorMessage
                viewModel.handleEvent(EditAnuncioEvent.DismissError)
                return@launch
            }

            val mime = context.contentResolver.getType(uri)
            val fileName = queryFileName(uri)

            viewModel.handleEvent(
                EditAnuncioEvent.UploadFoto(
                    bytes = bytes,
                    fileName = fileName,
                    mimeType = mime
                )
            )
        }
    }

    LaunchedEffect(state.successMessage) {
        if (state.successMessage != null) {
            if (state.successMessage!!.contains("atualizado")) {
                onSaved()
            }
        }
    }

    when {
        state.isLoading && state.anuncio == null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.errorMessage != null && state.anuncio == null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                androidx.compose.material3.Text(text = state.errorMessage ?: "Erro desconhecido")
            }
        }

        else -> {
            EditAnuncioScreen(
                    titulo = titulo,
                    descricao = descricao,
                    rua = rua,
                    numero = numero,
                    bairro = bairro,
                    cidade = cidade,
                    estado = estado,
                    valorAluguel = valorAluguel,
                    valorContas = valorContas,
                    vagasDisponiveis = vagasDisponiveis,
                    tipoImovel = tipoImovel,
                    comodos = comodos,
                    fotos = fotos,
                    isSaving = state.isSaving,
                    isUploadingPhoto = state.isUploadingPhoto,
                    onTituloChange = { viewModel.handleEvent(EditAnuncioEvent.UpdateTitulo(it)) },
                    onDescricaoChange = { viewModel.handleEvent(EditAnuncioEvent.UpdateDescricao(it)) },
                    onRuaChange = { viewModel.handleEvent(EditAnuncioEvent.UpdateRua(it)) },
                    onNumeroChange = { viewModel.handleEvent(EditAnuncioEvent.UpdateNumero(it)) },
                    onBairroChange = { viewModel.handleEvent(EditAnuncioEvent.UpdateBairro(it)) },
                    onCidadeChange = { viewModel.handleEvent(EditAnuncioEvent.UpdateCidade(it)) },
                    onEstadoChange = { viewModel.handleEvent(EditAnuncioEvent.UpdateEstado(it)) },
                    onValorAluguelChange = { viewModel.handleEvent(EditAnuncioEvent.UpdateValorAluguel(it)) },
                    onValorContasChange = { viewModel.handleEvent(EditAnuncioEvent.UpdateValorContas(it)) },
                    onVagasDisponiveisChange = { viewModel.handleEvent(EditAnuncioEvent.UpdateVagasDisponiveis(it)) },
                    onTipoImovelChange = { viewModel.handleEvent(EditAnuncioEvent.UpdateTipoImovel(it)) },
                    onComodosChange = { viewModel.handleEvent(EditAnuncioEvent.UpdateComodos(it)) },
                    onFotoAdded = { pickPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                    onFotoRemoved = { viewModel.handleEvent(EditAnuncioEvent.RemoveFoto(it)) },
                    onCancelClick = onCancel,
                    onSaveClick = { viewModel.handleEvent(EditAnuncioEvent.SaveAnuncio) },
                    errorMessage = state.errorMessage,
                    successMessage = state.successMessage
                )
        }
    }
}
