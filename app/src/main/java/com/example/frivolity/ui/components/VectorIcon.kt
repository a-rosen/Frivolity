package com.example.frivolity.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource

@Composable
fun VectorIcon(
    drawableId: Int,
    description: String,
) {
    val painter = rememberVectorPainter(
        image = ImageVector.vectorResource(id = drawableId)
    )

    Icon(
        painter = painter,
        contentDescription = description
    )

}