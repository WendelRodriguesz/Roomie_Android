package com.roomie.app.feature.profile.data.remote.dto
import com.roomie.app.feature.profile.model.PartyFrequency
import com.roomie.app.feature.profile.model.SleepRoutine
import com.roomie.app.feature.profile.model.CleaningHabit

data class InteressesInteressadoRequest(
    val frequencia_festas: PartyFrequency,
    val habitos_limpeza: CleaningHabit,
    val aceita_pets: Boolean,
    val horario_sono: SleepRoutine,
    val orcamento_min: Float,
    val orcamento_max: Float,
    val aceita_dividir_quarto: Boolean,
    val fumante: Boolean,
    val consome_bebidas_alcoolicas: Boolean,
)

data class InteressesInteressadoDto(
    val id: Long?,
    val frequencia_festas: PartyFrequency?,
    val habitos_limpeza: CleaningHabit?,
    val aceita_pets: Boolean?,
    val horario_sono: SleepRoutine?,
    val orcamento_min: Float?,
    val orcamento_max: Float?,
    val aceita_dividir_quarto: Boolean?,
    val fumante: Boolean?,
    val consome_bebidas_alcoolicas: Boolean?,
)

data class UsuarioInteressadoDto(
    val id: Long,
    val nome: String,
    val email: String?,
    val data_de_nascimento: String?,
    val idade: Int?,
    val cidade: String,
    val ocupacao: String?,
    val bio: String?,
    val genero: String?,
    val foto_de_perfil: String?,
    val interesses: InteressesInteressadoDto?,
)
