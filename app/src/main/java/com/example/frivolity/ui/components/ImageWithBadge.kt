package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.frivolity.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageWithBadge(
    imageUrl: String,
    badgeText: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
    ) {
        ImageWithPreview(
            url = imageUrl,
            drawableId = R.drawable.baseline_cruelty_free_24,
            modifier = Modifier
                .padding(4.dp)
                .defaultMinSize(64.dp, 64.dp)
        )
        Badge(
            content = { Text(text = badgeText) },
            modifier = Modifier
        )
    }
}