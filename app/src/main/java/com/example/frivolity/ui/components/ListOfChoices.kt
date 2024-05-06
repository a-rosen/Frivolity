package com.example.frivolity.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfChoices(
    choices: List<String>,
    onListItemClick: (String) -> Unit,
) {
    var selectedChoice by remember { mutableStateOf( "") }
    fun onSelectionChange(stringToSelect: String) {
        selectedChoice = stringToSelect
    }
    LazyColumn(
        modifier = Modifier
            .selectableGroup()
    ) {

        items(choices) {
            ListItem(
                headlineText = { Text(it) },
                modifier = Modifier
                    .clickable {
                        onSelectionChange(it)
                        onListItemClick(it)
                    },
                trailingContent = {
                    if (it == selectedChoice) {
                        Icon(
                            Icons.Filled.Done,
                            ""
                        )
                    }
                },
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