package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frivolity.ui.models.UiItemDetail

@Composable
fun ItemDetailHeader(
    itemDetail: UiItemDetail,
    numberOfListings: String,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
    ) {
        ImageWithBadge(
            imageUrl = "https://xivapi.com/${itemDetail.iconUrl}",
            badgeText = numberOfListings,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Text(
            text = itemDetail.name,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemDetailCardPreviewEquippable() {
    ItemDetailHeader(
        itemDetail = UiItemDetail(
            "Allagan Tomestone of Frivolity But Much Longer In Title",
            0,
            "",
            0,
            1,
            mapOf(Pair("Name", "ABC DEF GHI JKL MNO PQR STU")),
            description = "This sure is an item that does things.",
            type = "Arms"
        ),
        numberOfListings = "1"
    )
}

@Preview(showBackground = true)
@Composable
fun ItemDetailCardPreviewRegular() {
    ItemDetailHeader(
        itemDetail = UiItemDetail(
            "Allagan Tomestone of Frivolity",
            0,
            "",
            null,
            1,
            null,
            description = "This is a piece of furniture or something, you can't put it on.",
            type = "Outdoor Furniture"
        ),
        numberOfListings = "999"
    )
}