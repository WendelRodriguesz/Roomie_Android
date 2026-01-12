package com.roomie.app.feature.vaga.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.roomie.app.core.ui.components.GradientButton
import com.roomie.app.core.ui.components.ImagePickerBox
import com.roomie.app.core.ui.components.RoomieTextField
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme

enum class OcupacaoMorador { TRABALHA, ESTUDA, TRABALHA_E_ESTUDA, SEM_PREFERENCIA }
enum class PerfilSocial{ RESERVADO, SOCIAL, SEM_PREFERENCIA }

@Composable
fun CadastrarVagasScreen(navController: NavController){
    var endereco by remember { mutableStateOf("") }
    var aluguel by remember {mutableStateOf("")}
    var contas by remember {mutableStateOf("")}
    var moradoresAtuais by remember { mutableStateOf(1f) }
    var moradoresMax by remember { mutableStateOf(1f) }
    var idadeMorador by remember {mutableStateOf(15f)}
    var ocupacao by remember { mutableStateOf(OcupacaoMorador.SEM_PREFERENCIA) }
    var perfilSocial by remember {mutableStateOf(PerfilSocial.SEM_PREFERENCIA) }
    var fuma by remember { mutableStateOf(false) }
    var festas by remember { mutableStateOf(false) }
    var aceitaPets by remember { mutableStateOf(false) }
    var descricao by remember { mutableStateOf("") }
    var titulo by remember { mutableStateOf("") }
    val selectedImages = remember { mutableStateListOf<Uri>() }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(maxItems = 4)
    ) { uris ->
        selectedImages.clear()
        selectedImages.addAll(uris)
    }


    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
        ) {
            item{
                Text(
                    text = "Cadastrar Vaga",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Adicione as informações para que interessados compatíveis encontrem seu espaço",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(Modifier.height(20.dp))

            }

        item {
            Text("Fotos do imóvel", fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImagePickerBox(
                    imageUri = selectedImages.getOrNull(0),
                    onClick = {
                        imagePickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier.size(110.dp)
                )

                repeat(2) { index ->
                    val uri = selectedImages.getOrNull(index + 1)

                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .background(Color(0xFFEAEAEA), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (uri != null) {
                            AsyncImage(
                                model = uri,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(Icons.Default.Photo, contentDescription = "Foto")
                        }
                    }
                }
            }

            Spacer(Modifier.height(25.dp))
        }


        item {
            Text("Endereço aproximado", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))

            RoomieTextField(
                value = endereco,
                onValueChange = { endereco = it },
                placeholder = "Digite o endereço"
            )

            Spacer(Modifier.height(20.dp))
        }

        item {
            Text("Valores", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))

            RoomieTextField(
                value = aluguel,
                onValueChange = { aluguel = it },
                placeholder = "Valor do aluguel"
            )

            Spacer(Modifier.height(10.dp))

            RoomieTextField(
                value = contas,
                onValueChange = { contas = it },
                placeholder = "Valor médio das contas"
            )

            Spacer(Modifier.height(20.dp))
        }

        item {
            Text("Informações sobre a moradia", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(10.dp))

            Text("Número de moradores atuais: ${moradoresAtuais.toInt()}")
            Slider(
                value = moradoresAtuais,
                onValueChange = { moradoresAtuais = it },
                valueRange = 1f..10f
            )

            Spacer(Modifier.height(10.dp))

            Text("Número máximo de moradores: ${moradoresMax.toInt()}")
            Slider(
                value = moradoresMax,
                onValueChange = { moradoresMax = it },
                valueRange = 1f..10f
            )

            Spacer(Modifier.height(20.dp))
        }

        item {
            Text("Regras da casa", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = fuma, onCheckedChange = { fuma = it })
                Text("Proibido fumar")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = festas, onCheckedChange = { festas = it })
                Text("Proibido festas")
            }

            Spacer(Modifier.height(20.dp))
        }
        //preferencias de moradia
        item{
            Text("Perfil do morador ideal", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(10.dp))

            Text("Idade desejada: ${idadeMorador.toInt()}")
            Slider(
                value = idadeMorador,
                onValueChange = { idadeMorador = it },
                valueRange = 15f..50f
            )

            Spacer(Modifier.height(10.dp))

            Text("Ocupação desejada", fontWeight = FontWeight.Medium)

            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material3.RadioButton(
                    selected = ocupacao == OcupacaoMorador.TRABALHA,
                    onClick = { ocupacao = OcupacaoMorador.TRABALHA }
                )
                Text("Trabalha")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material3.RadioButton(
                    selected = ocupacao == OcupacaoMorador.ESTUDA,
                    onClick = { ocupacao = OcupacaoMorador.ESTUDA }
                )
                Text("Estuda")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material3.RadioButton(
                    selected = ocupacao == OcupacaoMorador.TRABALHA_E_ESTUDA,
                    onClick = { ocupacao = OcupacaoMorador.TRABALHA_E_ESTUDA }
                )
                Text("Trabalha e estuda")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material3.RadioButton(
                    selected = ocupacao == OcupacaoMorador.SEM_PREFERENCIA,
                    onClick = { ocupacao = OcupacaoMorador.SEM_PREFERENCIA }
                )
                Text("Sem preferência")
            }

            Spacer(Modifier.height(10.dp))

            Text("Perfil social ", fontWeight = FontWeight.Medium)

            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material3.RadioButton(
                    selected = perfilSocial == PerfilSocial.RESERVADO,
                    onClick = { perfilSocial = PerfilSocial.RESERVADO }
                )
                Text("Reservado")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material3.RadioButton(
                    selected = perfilSocial == PerfilSocial.SOCIAL,
                    onClick = { perfilSocial = PerfilSocial.SOCIAL }
                )
                Text("Sociável")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material3.RadioButton(
                    selected = perfilSocial == PerfilSocial.SEM_PREFERENCIA,
                    onClick = { perfilSocial = PerfilSocial.SEM_PREFERENCIA }
                )
                Text("Sem preferência")
            }
        }

        item {
            Text("Aceitação de pets", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = aceitaPets, onCheckedChange = { aceitaPets = it })
                Spacer(Modifier.width(12.dp))
                Text(if (aceitaPets) "Sim" else "Não")
            }

            Spacer(Modifier.height(20.dp))
        }

        item {
            Text("Descrição da vaga", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))

            RoomieTextField(
                value = descricao,
                onValueChange = { descricao = it },
                placeholder = "Escreva aqui...",
            )

            Spacer(Modifier.height(20.dp))
        }

        item {
            Text("Título da vaga", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))

            RoomieTextField(
                value = titulo,
                onValueChange = { titulo = it },
                placeholder = "Exemplo: Quarto disponível no Centro"
            )

            Spacer(Modifier.height(30.dp))
        }

        item {
            GradientButton(
                text = "Iniciar",
                buttonTextSize = 16,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = {
                }
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@RoomiePreview
@Composable
private fun CadastrarVagasPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        Surface {
            val navController = rememberNavController()
            CadastrarVagasScreen(navController = navController)

        }
    }
}