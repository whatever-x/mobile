package com.whatever.caramel.feature.content.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
fun LinkPreview(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String,
    onLinkPreviewClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(CaramelTheme.shape.s)
            .background(CaramelTheme.color.background.tertiary)
            .clickable { onLinkPreviewClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .size(width = 62.dp, height = 90.dp)
                    .clip(CaramelTheme.shape.s),
                contentScale = ContentScale.Crop
            )

            Text(
                text = title,
                style = CaramelTheme.typography.body4.regular,
                color = CaramelTheme.color.text.brand
            )
        }
    }
} 