package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.frivolity.R


@Composable
fun ButtonWithDropdown(
    icon: Int,
    iconDescription: String,
    menuItems: List<String>,
    onItemClicked: (String) -> Unit,
    onIconClicked: () -> Unit,
    expanded: Boolean,
) {
    Box(
        modifier = Modifier
    ) {
        IconButton(
            onClick = { onIconClicked() },
        ) {
            VectorIcon(
                drawableId = icon,
                description = iconDescription
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onIconClicked() },
        ) {
            menuItems.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        onItemClicked(it)
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
        onItemClicked = {},
        onIconClicked = {},
        expanded = false
    )
}