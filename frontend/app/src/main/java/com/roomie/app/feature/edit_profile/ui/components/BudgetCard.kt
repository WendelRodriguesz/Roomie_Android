package com.roomie.app.feature.edit_profile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme

@Composable
fun BudgetCard(
    minBudget: Int,
    onMinBudgetChange: (Int) -> Unit,
    maxBudget: Int,
    onMaxBudgetChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Orçamento mensal",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Column {
                    Text(
                        text = "Mínimo: R$ $minBudget",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Slider(
                        value = minBudget.toFloat(),
                        onValueChange = { onMinBudgetChange(it.toInt()) },
                        valueRange = 500f..4000f,
                        steps = 34
                    )
                }

                Column {
                    Text(
                        text = "Máximo: R$ $maxBudget",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Slider(
                        value = maxBudget.toFloat(),
                        onValueChange = { onMaxBudgetChange(it.toInt()) },
                        valueRange = minBudget.toFloat()..5000f,
                        steps = ((5000 - minBudget) / 100).coerceAtMost(44)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                ) {
                    Text(
                        text = "R$ $minBudget - R$ $maxBudget /mês",
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@RoomiePreview
@Composable
private fun BudgetCardPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        BudgetCard(
            minBudget = 800,
            onMinBudgetChange = {},
            maxBudget = 1200,
            onMaxBudgetChange = {}
        )
    }
}

