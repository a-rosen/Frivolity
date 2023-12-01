package com.example.frivolity.ui.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ButtonWithDropdown(
    displayText: String,
    menuItems: List<String>,
    onItemClicked: (String) -> Unit,
) {
    var shouldShowDropdown by remember { mutableStateOf(false) }

    TextButton(
        onClick = { shouldShowDropdown = true },
        content = { Text(text = displayText) }
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
    ButtonWithDropdown(
        displayText = "Preview",
        onItemClicked = {},
        menuItems = listOf(
            "One",
            "Two",
            "Three"
        )
    )
}