package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.frivolity.ui.models.UiListingDetail

@Composable
fun MarketItemListItem(
    item: UiListingDetail
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if (item.hq) {
            AsyncImage(model = "https://xivapi.com/img-misc/hq.png", contentDescription = "")
        }
        Text(text = item.retainerName)
        Text(text = item.quantity.toString())
        Text(text = item.pricePerUnit.toString())
        Text(text = item.total.toString())
    }
}

@Preview
@Composable
fun MarketItemListItemPreview() {
    MarketItemListItem(
        item = UiListingDetail(
            50, 3, true, "RetainerNameLol", 2, 150
        )
    )
}