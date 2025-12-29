package com.roomie.app.feature.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.UserMock
import com.roomie.app.feature.profile.model.UserProfile
import com.roomie.app.feature.profile.ui.components.ProfileAvatar

@Composable
fun ProfileHeaderCard(profile: UserProfile) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileAvatar(profile)

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    val title = if (profile.age != null) {
                        "${profile.name}, ${profile.age}"
                    } else {
                        profile.name
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = profile.professionOrCourse.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = profile.city,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (profile.bio.isNotBlank()) {
                Text(
                    text = profile.bio,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            val minBudget = profile.budget.minBudget
            val maxBudget = profile.budget.maxBudget
            if (minBudget != null || maxBudget != null) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = buildString {
                            append("R$ ")
                            append(minBudget ?: maxBudget ?: 0)
                            append(" - R$ ")
                            append(maxBudget ?: minBudget ?: 0)
                            append("/mês")
                        },
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Tags (um futuro próximo)
//            if (profile.lifestyle.tags.isNotEmpty()) {
//                FlowRow(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    profile.lifestyle.tags.forEach { tag ->
//                        AssistChip(
//                            onClick = {},
//                            label = { Text(tag) },
//                            colors = SuggestionChipDefaults.suggestionChipColors(
//                                containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.70f)
//                            ),
////                            border = null,
//                            shape = RoundedCornerShape(15.dp)
//                        )
//                    }
//                }
//            }
        }
    }
}

@RoomiePreview
@Composable
private fun ProfileHeaderCardPreview(){
    Roomie_AndroidTheme (dynamicColor = false){
        ProfileHeaderCard(UserMock.profileRoomie1)
    }
}