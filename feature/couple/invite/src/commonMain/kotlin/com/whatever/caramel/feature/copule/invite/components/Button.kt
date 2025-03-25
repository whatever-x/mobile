package com.whatever.caramel.feature.copule.invite.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun RowScope.InviteButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: DrawableResource,
    onClickButton: () -> Unit
) {
    Column(
        modifier = modifier
            .weight(weight = 1f)
            .background(
                color = CaramelTheme.color.background.tertiary,
                shape = CaramelTheme.shape.m
            )
            .clip(shape = CaramelTheme.shape.m)
            .clickable(onClick = onClickButton)
            .padding(
                vertical = CaramelTheme.spacing.xxl
            ),
        verticalArrangement = Arrangement.spacedBy(
            space = CaramelTheme.spacing.m
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(resource = icon),
            tint = CaramelTheme.color.icon.primary,
            contentDescription = null
        )

        Text(
            text = text,
            style = CaramelTheme.typography.body2.bold,
            color = CaramelTheme.color.text.primary,
            textAlign = TextAlign.Center
        )
    }
}