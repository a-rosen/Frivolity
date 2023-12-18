package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
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
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ) {
            Text(text = "iLvl: ${itemDetail.iLevel.toString()}")
            Text(text = itemDetail.jobToEquip ?: "")
            Text(text = "equippable: ${itemDetail.levelToEquip.toString()}")
        }
    }
}

@Preview
@Composable
fun ItemDetailCardPreview() {
    ItemDetailCard(
        itemDetail = UiItemDetail(
            "Allagan Tomestone of Frivolity",
            0,
            "",
            0,
            1,
            "ACN SMN RDM BLU"
        ),
    )
}