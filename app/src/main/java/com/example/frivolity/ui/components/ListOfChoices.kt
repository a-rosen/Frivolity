package com.example.frivolity.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfChoices(
    choices: List<String>,
    onClick: () -> Unit,
) {
    Column {
        choices.forEach { choiceName ->
            ListItem(
                headlineText = { Text(text = choiceName) },
                modifier = Modifier
                    .clickable { onClick() })
        }
    }
}

@Preview
@Composable
fun ListOfChoicesPreview() {
    ListOfChoices(
        choices = listOf("First", "Second", "Third"),
        onClick = {}
    )
}