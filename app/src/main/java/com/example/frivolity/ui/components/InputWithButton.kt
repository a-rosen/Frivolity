package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputWithButton(
    onButtonClick: () -> Unit,
    onTextChange: (String) -> Unit,
    displayedText: String,
) {
    Row {
        OutlinedTextField(
            value = displayedText,
            onValueChange = { onTextChange(it) }
        )
        Button(
            onClick = { onButtonClick() }
        ) {
            Text(text = "Click me pls")
        }
    }

}