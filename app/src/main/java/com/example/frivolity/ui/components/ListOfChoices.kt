package com.example.frivolity.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfChoices(
    choices: List<String>,
    onListItemClick: (String) -> Unit,
) {
    LazyColumn() {
        items(choices) {
            Text(
                text = it,
                modifier = Modifier
                    .clickable { onListItemClick(it) }
                    .padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun ListOfChoicesPreview() {
    ListOfChoices(
        choices = listOf("First", "Second", "Third"),
        onListItemClick = {}
    )
}