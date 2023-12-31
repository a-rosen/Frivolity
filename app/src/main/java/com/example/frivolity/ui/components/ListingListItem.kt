package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

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
                    isLoading = false,
                    drawableId = 0
                )
            },
            headlineText = { Text(headlineText)},
            supportingText = { Text(supportingText) },
            trailingContent =
            {
                if (isHq) {
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
