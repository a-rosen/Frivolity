package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.frivolity.ui.models.SortMethods
import com.example.frivolity.ui.screens.ItemDetailScreenState

@Composable
fun StatsRow(
    state: ItemDetailScreenState,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (state.sortMethod == SortMethods.TOTAL) {
            state.cheapestTotalPriceDc?.let {
                CheapestColumn(
                    text = "CHEAPEST (DC)",
                    prices = it,
                    state = state
                )
            }
        } else {
            state.cheapestUnitPriceDc?.let {
                CheapestColumn(
                    text = "CHEAPEST (DC)",
                    prices = it,
                    state = state
                )
            }
        }

        if (state.sortMethod == SortMethods.TOTAL) {
            state.cheapestTotalPriceRegion?.let {
                CheapestColumn(
                    text = "CHEAPEST (Region)",
                    prices = it,
                    state = state
                )
            }
        } else {
            state.cheapestUnitPriceRegion?.let {
                CheapestColumn(
                    text = "CHEAPEST (Region)",
                    prices = it,
                    state = state
                )
            }
        }
    }
}


@Preview
@Composable
fun StatsRowPreview() {
    StatsRow(
        ItemDetailScreenState.EMPTY,
    )
}