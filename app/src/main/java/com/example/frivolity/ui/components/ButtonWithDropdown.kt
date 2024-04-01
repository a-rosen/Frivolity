package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.frivolity.R


@Composable
fun ButtonWithDropdown(
    icon: Int,
    iconDescription: String,
    menuItems: List<String>,
    onItemClicked: (String) -> Unit,
) {
    var shouldShowDropdown by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
    ) {
        IconButton(
            onClick = { shouldShowDropdown = true },
        ) {
            VectorIcon(
                drawableId = icon,
                description = iconDescription
            )
        }
        DropdownMenu(
            expanded = shouldShowDropdown,
            onDismissRequest = { shouldShowDropdown = false },
        ) {
            menuItems.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        onItemClicked(it)
                        shouldShowDropdown = false
                    }
                )
            }
        }
    }

}

@Preview
@Composable
fun ButtonWithDropdownPreview(
) {
    ButtonWithDropdown(
        icon = R.drawable.ic_group,
        iconDescription = "",
        menuItems = listOf(),
        onItemClicked = {}
    )
}