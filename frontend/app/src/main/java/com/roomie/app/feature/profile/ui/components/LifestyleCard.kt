package com.roomie.app.feature.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roomie.app.core.model.ProfileRole
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.CleaningHabit
import com.roomie.app.feature.profile.model.PartyFrequency
import com.roomie.app.feature.profile.model.SleepRoutine
import com.roomie.app.feature.profile.model.UserMock
import com.roomie.app.feature.profile.model.UserProfile

@Composable
fun LifestyleCard(
    profile: UserProfile,
    role: ProfileRole,
    hasInterests: Boolean = true,
    onEditPreferencesClick: () -> Unit = {}
) {
    val lifestyle = profile.lifestyle
    val budget = profile.budget

    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Preferências de convivência",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                TextButton(
                    onClick = onEditPreferencesClick
                ) {
                    Text(
                        text = if (hasInterests) "Editar" else "Cadastrar interesses",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            InfoRow(
                label = "Gosta de ir a festas",
                value = if (hasInterests) {
                    when (lifestyle.partyFrequency) {
                        PartyFrequency.FREQUENTE -> "Sempre"
                        PartyFrequency.AS_VEZES -> "Às vezes"
                        PartyFrequency.NUNCA -> "Nunca"
                    }
                } else "Não informado"
            )

            InfoRow(
                label = "Frequência de limpeza",
                value = if (hasInterests) {
                    when (lifestyle.cleaningHabit) {
                        CleaningHabit.DIARIO -> "Diária"
                        CleaningHabit.OCASIONAL -> "Ocasionalmente"
                        CleaningHabit.QUINZENAL -> "Quinzenalmente"
                        CleaningHabit.SEMANAL -> "Semanalmente"
                    }
                } else "Não informado"
            )

            InfoRow(
                label = "Rotina de sono",
                value = if (hasInterests) {
                    when (lifestyle.sleepRoutine) {
                        SleepRoutine.MATUTINO -> "Matutino"
                        SleepRoutine.NOTURNO -> "Noturno"
                        SleepRoutine.VESPERTINO -> "Vespertino"
                        SleepRoutine.FLEXIVEL -> "Flexível"
                    }
                } else "Não informado"
            )

            InfoRow(
                label = "Aceita pets",
                value = if (hasInterests) {
                    if (lifestyle.acceptsPets) "Sim" else "Não"
                } else "Não informado"
            )

            InfoRow(
                label = "Aceita dividir quarto",
                value = if (hasInterests) {
                    if (lifestyle.acceptsSharedRoom) "Sim" else "Não"
                } else "Não informado"
            )

            // Só para interessado
            if (role == ProfileRole.SEEKER) {
                InfoRow(
                    label = "Orçamento mínimo",
                    value = if (hasInterests) {
                        budget.minBudget?.toString() ?: "Não informado"
                    } else "Não informado"
                )
                InfoRow(
                    label = "Orçamento máximo",
                    value = if (hasInterests) {
                        budget.maxBudget?.toString() ?: "Não informado"
                    } else "Não informado"
                )
                InfoRow(
                    label = "Fumante",
                    value = if (hasInterests) {
                        if (lifestyle.isSmoker) "Sim" else "Não"
                    } else "Não informado"
                )
                InfoRow(
                    label = "Consome bebidas alcoólicas",
                    value = if (hasInterests) {
                        if (lifestyle.isDrinker) "Sim" else "Não"
                    } else "Não informado"
                )
            }
        }
    }
}

@RoomiePreview
@Composable
private fun LifestyleCardPreview(){
    Roomie_AndroidTheme (dynamicColor = false){
        LifestyleCard(
            profile = UserMock.profileRoomie1,
            role = ProfileRole.SEEKER
        )
    }
}