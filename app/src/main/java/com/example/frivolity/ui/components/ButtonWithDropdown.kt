package com.example.frivolity.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ButtonWithDropdown(
    toggleDropdown: () -> Unit
) {
    Button(
        onClick = { toggleDropdown }
    ) {

    }
    DropdownMenu(
        expanded = true,
        onDismissRequest = { toggleDropdown }) {
//        for (value in DataCenter.values()) {
//            DropdownMenuItem(text = { Text(
//                text = value.name) }, onClick = { /*TODO*/ })
        }
    }


@Preview
@Composable
fun ButtonWithDropdownPreview() {
    ButtonWithDropdown(
        toggleDropdown = {}
    )
}