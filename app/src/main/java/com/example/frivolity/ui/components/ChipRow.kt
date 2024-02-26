package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.frivolity.ui.screens.ItemDetailScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipRow(
    state: ItemDetailScreenState,
    sortLabel: String,
    onSortClick: () -> Unit,
    onHqOnlyClick: () -> Unit,
    onDcClick: (String) -> Unit,
    onWorldClick: (String) -> Unit,
    worldNameList: List<String>,
    dcNameList: List<String>,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        FilterChip(
            selected = false,
            onClick = { onSortClick() },
            label = { Text(text = sortLabel) })
        FilterChip(
            selected = state.showHqOnly,
            onClick = { onHqOnlyClick() },
            label = { Text(text = "HQ Only") })
        ChipWithDropdown(
            displayText = "Switch DC",
            menuItems = dcNameList,
            onItemClicked = { onDcClick(it) }
        )
        ChipWithDropdown(
            displayText = "Switch World",
            menuItems = worldNameList,
            onItemClicked = { onWorldClick(it) }
        )

    }

}

@Composable
@Preview
fun ChipRowPreview() {
    ChipRow(ItemDetailScreenState.EMPTY, "Sort", {}, {}, {}, {}, listOf(), listOf())
}