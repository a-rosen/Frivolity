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


@Composable
fun ButtonWithDropdown(
    displayText: String,
    menuItems: List<String>
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
                onClick = { TODO() }
            )
        }
    }
}


//@Preview
//@Composable
//fun ButtonWithDropdownPreview() {
//    ButtonWithDropdown(
//        toggleDropdown = {},
//        menuItems = listOf(
//            "One",
//            "Two",
//            "Three"
//        )
//    )
//}