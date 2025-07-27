package com.whatever.caramel.feature.home.components.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun NudgeCard(
    modifier: Modifier = Modifier,
    text: String,
    iconResource: DrawableResource,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = CaramelTheme.color.fill.quaternary,
                    shape = CaramelTheme.shape.l,
                ).clip(shape = CaramelTheme.shape.l)
                .clickable(
                    onClick = onClick,
                    indication = null,
                    interactionSource = null,
                ).padding(all = CaramelTheme.spacing.l),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = CaramelTheme.typography.body3.bold,
            color = CaramelTheme.color.text.primary,
        )

        Icon(
            painter = painterResource(resource = iconResource),
            tint = CaramelTheme.color.icon.tertiary,
            contentDescription = null,
        )
    }
}
