package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frivolity.network.models.universalisapi.ApiPrices
import com.example.frivolity.ui.models.SortMethods
import com.example.frivolity.ui.screens.ItemDetailScreenState

@Composable
fun CheapestColumn(
    text: String,
    prices: ApiPrices,
    state: ItemDetailScreenState,
) {

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium
        )

        if (state.sortMethod == SortMethods.TOTAL) {
            Text(
                text = "%,d".format(prices.total),
                style = MaterialTheme.typography.headlineSmall,
            )
        } else {
            Text(
                text = "%,d".format(prices.pricePerUnit),
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        Text(
            text = prices.worldName,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview
@Composable
fun CheapestColumnPreview() {
    CheapestColumn(
        text = "CHEAPEST",
        prices = ApiPrices(
            3, 150, 502345, "Faerie"
        ),
        state = ItemDetailScreenState.EMPTY
    )
}