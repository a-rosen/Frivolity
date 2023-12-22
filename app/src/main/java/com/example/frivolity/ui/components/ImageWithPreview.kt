package com.example.frivolity.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun ImageWithPreview(
    url: String,
    isLoading: Boolean,
    drawableId: Int,
    modifier: Modifier = Modifier,
) {
    val isInPreview = LocalInspectionMode.current

    if (isLoading) {
        Image(
            painter = painterResource(id = drawableId),
            contentScale = ContentScale.Fit,
            contentDescription = "Loading...",
            modifier = modifier
        )
    } else {
        val painter: Painter
        val scaling: ContentScale

        if (isInPreview) {
            painter = ColorPainter(color = Color.Blue)
            scaling = ContentScale.Crop
        } else {
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .placeholder(drawableId)
                    .size(Size.ORIGINAL) // Set the target size to load the image at.
                    .build()
            )

            scaling = when (painter.state) {
                is AsyncImagePainter.State.Success -> {
                    ContentScale.Crop
                }

                is AsyncImagePainter.State.Loading -> {
                    ContentScale.Fit
                }

                else -> {
                    ContentScale.FillBounds
                }
            }
        }

        Image(
            painter = painter,
            contentScale = scaling,
            contentDescription = "Icon",
            modifier = modifier
                .size(36.dp)
        )
    }
}