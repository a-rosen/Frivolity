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
import com.example.frivolity.ui.models.SortMethods
import com.example.frivolity.ui.screens.ItemDetailScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipRow(
    onTotalSortClick: () -> Unit,
    onUnitSortClick: () -> Unit,
    onHqOnlyClick: () -> Unit,
    state: ItemDetailScreenState,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        FilterChip(
            selected = state.sortMethod == SortMethods.TOTAL,
            onClick = { onTotalSortClick() },
            label = { Text(text = "Sort By Total") })
        FilterChip(
            selected = state.sortMethod == SortMethods.UNIT,
            onClick = { onUnitSortClick() },
            label = { Text(text = "Sort By Unit") })
        FilterChip(
            selected = state.showHqOnly,
            onClick = { onHqOnlyClick() },
            label = { Text(text = "HQ Only") })

    }

}

@Composable
@Preview
fun ChipRowPreview() {
    ChipRow({}, {}, {}, ItemDetailScreenState.EMPTY)
}