package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.frivolity.ui.theme.PurpleGrey40

@Composable
fun ItemListItem(
    iconUrl: String,
    itemName: String = "Item Name Placeholder",
    itemLevel: Int = 625,

    ) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .build(),
            contentDescription = "",
            placeholder = ColorPainter(color = PurpleGrey40)
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