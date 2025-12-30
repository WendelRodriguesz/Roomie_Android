package com.roomie.app.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roomie.app.core.ui.components.BudgetCard
import com.roomie.app.core.ui.preview.RoomiePreview
import com.roomie.app.core.ui.theme.Roomie_AndroidTheme
import com.roomie.app.feature.profile.model.*
import com.roomie.app.feature.edit_profile.ui.components.BasicInfoCard
import com.roomie.app.feature.edit_profile.ui.components.LifestylePreferencesCard

@Composable
fun LabeledOutlinedField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    supportingText: @Composable (() -> Unit)? = null,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
            label = null,
            placeholder = null,
            supportingText = supportingText
        )
    }
}

@RoomiePreview
@Composable
private fun LabeledOutlinedFieldPreview() {
    Roomie_AndroidTheme(dynamicColor = false) {
        var name by remember { mutableStateOf("Teste") }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LabeledOutlinedField(
                title = "Nome",
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }
    }
}


