package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingListItem(
    headlineText: String,
    supportingText: String,
    isHq: Boolean
) {
    Column {
        ListItem(
            leadingContent = {
                ImageWithPreview(
                    url = "https://xivapi.com/img-misc/061575.png",
                    drawableId = 0
                )
            },
            headlineText = {
                Text(
                    text = headlineText,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            supportingText = {
                Text(
                    text = supportingText,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            trailingContent =
            {
                if (isHq) {
                    ImageWithPreview(
                        url = "https://xivapi.com/img-misc/hq.png",
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
fun ListingListItemPreview() {
    ListingListItem(
        headlineText = "696,969,420",
        supportingText = "1 x 696,969,420",
        isHq = true
    )
}
