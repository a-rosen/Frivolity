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
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text =
                if (itemDetail.jobToEquip?.get("Name") != null) {
                    itemDetail.jobToEquip["Name"].toString()
                } else {
                    ""
                }
            )
            Text(text = "iLvl: ${itemDetail.iLevel}")
            Text(text = "Type: ${itemDetail.type}")
            Text(text = "equippable: ${itemDetail.levelToEquip.toString()}")

        }
        Text(
            text = "\"${itemDetail.description}\"",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview
@Composable
fun ItemDetailCardPreviewEquippable() {
    ItemDetailCard(
        itemDetail = UiItemDetail(
            "Allagan Tomestone of Frivolity",
            0,
            "",
            0,
            1,
            mapOf(Pair("Name", "ACN BLM RDM SMN")),
            description = "This sure is an item that does things.",
            type = "Arms"
        ),
    )
}

@Preview
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