package com.roomie.app.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import kotlin.math.roundToInt

@Composable
fun BudgetCard(
    minBudget: Int,
    maxBudget: Int,
    onMinBudgetChange: (Int) -> Unit,
    onMaxBudgetChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val minValue = 500
    val maxValue = 5000
    val stepSize = 100
    val steps = ((maxValue - minValue) / stepSize) - 1

    fun snapToStep(v: Float): Int {
        val snapped = (v / stepSize.toFloat()).roundToInt() * stepSize
        if (snapped < minValue) return minValue
        if (snapped > maxValue) return maxValue
        return snapped
    }

    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Orçamento mensal",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Column {
                    Text(
                        text = "Min: R$ $minBudget – Max: R$  $maxBudget",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    RangeSlider(
                        value = minBudget.toFloat()..maxBudget.toFloat(),
                        onValueChange = { newRange ->
                            val newMin = snapToStep(newRange.start)
                            val newMax = snapToStep(newRange.endInclusive)

                            val adjustedMin = minOf(newMin, newMax)
                            val adjustedMax = maxOf(newMin, newMax)

                            onMinBudgetChange(adjustedMin)
                            onMaxBudgetChange(adjustedMax)
                        },
                        valueRange = minValue.toFloat()..maxValue.toFloat(),
                        steps = steps,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.07f)
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
