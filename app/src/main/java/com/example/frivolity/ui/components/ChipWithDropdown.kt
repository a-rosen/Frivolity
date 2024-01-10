package com.example.frivolity.ui.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipWithDropdown(
    displayText: String,
    menuItems: List<String>,
    onItemClicked: (String) -> Unit,
) {
    var shouldShowDropdown by remember { mutableStateOf(false) }

    FilterChip(
        onClick = { shouldShowDropdown = true },
        label = { Text(text = displayText) },
        selected = shouldShowDropdown
    )
    DropdownMenu(
        expanded = shouldShowDropdown,
        onDismissRequest = { shouldShowDropdown = false }
    ) {
        menuItems.forEach {
            DropdownMenuItem(
                text = { Text(text = it) },
                onClick = { onItemClicked(it) }
            )
        }
    }
}


@Preview
@Composable
fun ButtonWithDropdownPreview() {
    ChipWithDropdown(
        displayText = "Preview",
        onItemClicked = {},
        menuItems = listOf(
            "One",
            "Two",
            "Three"
        )
    )
}