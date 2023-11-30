package com.example.frivolity.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ButtonWithDropdown(
    toggleDropdown: () -> Unit,
    menuItems: List<String>
) {
    Button(
        onClick = { toggleDropdown }
    ) {

    }
    DropdownMenu(
        expanded = true,
        onDismissRequest = { toggleDropdown }
    ) {
        menuItems.forEach {
            DropdownMenuItem(
                text = { Text(text = it) },
                onClick = { TODO() }
            )
        }
    }
}


@Preview
@Composable
fun ButtonWithDropdownPreview() {
    ButtonWithDropdown(
        toggleDropdown = {},
        menuItems = listOf(
            "One",
            "Two",
            "Three"
        )
    )
}