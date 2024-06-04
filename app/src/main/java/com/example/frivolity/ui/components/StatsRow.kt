package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.frivolity.ui.Asynchronous
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
            when (state.cheapestTotalPriceDc) {
                is Asynchronous.Error -> TODO()
                is Asynchronous.Loading -> TODO()
                is Asynchronous.Success ->
                    CheapestColumn(
                        text = "CHEAPEST (DC)",
                        prices = state.cheapestTotalPriceDc.resultData,
                        state = state
                    )

                is Asynchronous.Uninitialized -> TODO()
            }
            when (state.cheapestTotalPriceRegion) {
                is Asynchronous.Error -> TODO()
                is Asynchronous.Loading -> TODO()
                is Asynchronous.Success ->
                    CheapestColumn(
                        text = "CHEAPEST (Region)",
                        prices = state.cheapestTotalPriceRegion.resultData,
                        state = state
                    )

                is Asynchronous.Uninitialized -> TODO()
            }
        } else {
            when (state.cheapestUnitPriceDc) {
                is Asynchronous.Error -> TODO()
                is Asynchronous.Loading -> TODO()
                is Asynchronous.Success ->
                    CheapestColumn(
                        text = "CHEAPEST (DC)",
                        prices = state.cheapestUnitPriceDc.resultData,
                        state = state
                    )

                is Asynchronous.Uninitialized -> TODO()
            }
            when (state.cheapestUnitPriceRegion) {
                is Asynchronous.Error -> TODO()
                is Asynchronous.Loading -> TODO()
                is Asynchronous.Success ->
                    CheapestColumn(
                        text = "CHEAPEST (DC)",
                        prices = state.cheapestUnitPriceRegion.resultData,
                        state = state
                    )

                is Asynchronous.Uninitialized -> TODO()
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