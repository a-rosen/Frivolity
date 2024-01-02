package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frivolity.R
import com.example.frivolity.ui.models.UiItemDetail

@Composable
fun ItemDetailCard(
    itemDetail: UiItemDetail,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        ImageWithPreview(
            url = "https://xivapi.com/${itemDetail.iconUrl}",
            isLoading = false,
            drawableId = R.drawable.baseline_cruelty_free_24,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp)
                .defaultMinSize(48.dp, 48.dp)
        )
        Text(
            text = itemDetail.name,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally)
        )

        if (itemDetail.jobToEquip?.get("Name") != null) {
            Text(
                text = itemDetail.jobToEquip["Name"].toString(),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ) {
            Text(text = "iLevel: ${itemDetail.iLevel}")
            if (itemDetail.type != null) {
                Text(text = "Type: ${itemDetail.type}")
            }
            if (itemDetail.levelToEquip != null) {
                Text(text = "Level To Equip: ${itemDetail.levelToEquip}")
            }
        }
        Text(
            text = itemDetail.description,
            style = MaterialTheme.typography.bodySmall,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemDetailCardPreviewEquippable() {
    ItemDetailCard(
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
    )
}

@Preview(showBackground = true)
@Composable
fun ItemDetailCardPreviewRegular() {
    ItemDetailCard(
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
    )
}