package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.frivolity.ui.models.UiListingDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketItemListItemByTotal(
    item: UiListingDetail
) {
    Column {
        ListItem(
            headlineText = { Text(item.total.toString()) },
            supportingText = { Text("${item.quantity} x ${item.pricePerUnit}") },
            trailingContent =
            {
                if (item.hq) {
                    ImageWithPreview(
                        url = "https://xivapi.com/img-misc/hq.png",
                        isLoading = false,
                        drawableId = 0
                    )
                }
            },
        )
        Divider()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketItemListItemByUnit(
    item: UiListingDetail
) {
    Column {
        ListItem(
            leadingContent = {
                ImageWithPreview(
                    url = "https://xivapi.com/img-misc/061575.png",
                    isLoading = false,
                    drawableId = 0
                )
            },
            headlineText = { Text(item.pricePerUnit.toString()) },
            supportingText = { Text("${item.total} for ${item.quantity}") },
            trailingContent =
            {
                if (item.hq) {
                    ImageWithPreview(
                        url = "https://xivapi.com/img-misc/hq.png",
                        isLoading = false,
                        drawableId = 0
                    )
                }
            },
        )
        Divider()
    }
}

@Preview
@Composable
fun MarketItemListItemPreview() {
    MarketItemListItemByTotal(
        item = UiListingDetail(
            50, 3, true, "RetainerNameLol", 2, 150
        )
    )
}

@Preview
@Composable
fun MarketItemListItemUnitPreview() {
    MarketItemListItemByUnit(
        item = UiListingDetail(
            50, 3, true, "RetainerNameLol", 2, 150
        )
    )

}