package com.example.frivolity.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.frivolity.ui.theme.PurpleGrey40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListItem(
    iconUrl: String,
    itemName: String = "Item Name Placeholder",
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        ListItem(
            headlineText = { Text(text = itemName) },
            leadingContent = {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(iconUrl)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    placeholder = ColorPainter(color = PurpleGrey40),
                    modifier = Modifier
                        .size(36.dp)
                )
            }

        )


    }
}

@Preview
@Composable
fun ItemListItemPreview() {
    ItemListItem(
        iconUrl = "",
        onClick = {},
    )
}