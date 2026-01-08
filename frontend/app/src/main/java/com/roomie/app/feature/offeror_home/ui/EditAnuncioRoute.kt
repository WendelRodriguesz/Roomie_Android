package com.roomie.app.feature.offeror_home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roomie.app.core.data.api.RetrofitClient
import com.roomie.app.feature.offeror_home.data.AnuncioRepository
import com.roomie.app.feature.offeror_home.presentation.EditAnuncioEvent
import com.roomie.app.feature.offeror_home.presentation.EditAnuncioViewModel
import com.roomie.app.feature.offeror_home.presentation.EditAnuncioViewModelFactory

@Composable
fun EditAnuncioRoute(
    anuncioId: Long,
    token: String,
    onCancel: () -> Unit,
    onSaved: () -> Unit
) {
    val repository = remember { AnuncioRepository(RetrofitClient.anuncioApiService) }
    val viewModel: EditAnuncioViewModel = viewModel(
        factory = EditAnuncioViewModelFactory(repository, anuncioId, token)
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

    LaunchedEffect(state.successMessage) {
        if (state.successMessage != null) {
            onSaved()
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
                isSaving = state.isSaving,
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
                onCancelClick = onCancel,
                onSaveClick = { viewModel.handleEvent(EditAnuncioEvent.SaveAnuncio) },
                errorMessage = state.errorMessage
            )
        }
    }
}
