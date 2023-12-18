package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frivolity.network.models.universalisapi.ApiPrices

@Composable
fun CheapestCard(
    prices: ApiPrices

) {
    Card() {
        Column(modifier = Modifier
            .padding(8.dp)){
            Text(
                text = "CHEAPEST",
                style = MaterialTheme.typography.labelMedium)
            Text(text = "World: ${prices.worldName}")
            Text(text = "Quantity: ${prices.quantity}")
            Text(text = "Total: ${prices.total}")
        }

    }

}

@Preview
@Composable
fun CheapestCardPreview() {
    CheapestCard(
        prices = ApiPrices(
            3, 150, "Faerie"
        )
    )
}