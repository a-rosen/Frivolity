package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
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
import com.example.frivolity.R
import com.example.frivolity.ui.models.UiItemDetail

@Composable
fun ItemDetailHeader(
    itemDetail: UiItemDetail,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
    ) {
        ImageWithPreview(
            url = "https://xivapi.com/${itemDetail.iconUrl}",
            drawableId = R.drawable.baseline_cruelty_free_24,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .defaultMinSize(64.dp, 64.dp)
                .padding(4.dp)
        )
        Text(
            text = itemDetail.name,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
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
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ItemDetailCardPreviewRegular() {
    ItemDetailHeader(
        itemDetail = UiItemDetail(
            "Allagan Tomestone",
            0,
            "",
            null,
            1,
            null,
            description = "This is a piece of furniture or something, you can't put it on.",
            type = "Outdoor Furniture"
        )
    )
}