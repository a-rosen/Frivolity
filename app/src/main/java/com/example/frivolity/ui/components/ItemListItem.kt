package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ItemListItem(
    iconUrl: String,
    itemName: String = "Item Name Placeholder",
    itemLevel: Int = 625,

    ) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .build(),
            contentDescription = ""
        )

        Text(text = itemName)

        Text(text = itemLevel.toString())
    }
}

@Preview
@Composable
fun ItemListItemPreview() {
    ItemListItem(
        iconUrl = "",
    )
}