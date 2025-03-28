package com.whatever.caramel.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun CaramelBalloonPopupWithImage(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    text: String,
    image: DrawableResource,
    contentScale: ContentScale,
    contentDescription: String? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = CaramelTheme.spacing.s,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CaramelBalloonPopup(text = text)

        Image(
            modifier = imageModifier,
            painter = painterResource(resource = image),
            contentScale = contentScale,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun CaramelBalloonPopup(
    modifier: Modifier = Modifier,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier
                .background(
                    color = CaramelTheme.color.fill.quaternary,
                    shape = CaramelTheme.shape.s
                )
                .padding(
                    horizontal = CaramelTheme.spacing.l,
                    vertical = CaramelTheme.spacing.m
                ),
            textAlign = TextAlign.Center,
            text = text,
            style = CaramelTheme.typography.body4.regular,
            color = CaramelTheme.color.text.primary
        )

        Icon(
            modifier = Modifier.offset(y = (-0.2).dp),
            painter = painterResource(resource = Resources.Icon.ic_vertex_17_9),
            contentDescription = null,
            tint = CaramelTheme.color.fill.quaternary
        )
    }
}