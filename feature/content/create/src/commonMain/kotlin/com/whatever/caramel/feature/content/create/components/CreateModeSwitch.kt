package com.whatever.caramel.feature.content.create.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.content.create.mvi.ContentState
import org.jetbrains.compose.resources.painterResource

enum class CreateMode {
    MEMO, CALENDAR
}

@Composable
fun CreateModeSwitch(
    modifier: Modifier = Modifier,
    createMode: CreateMode,
    onCreateModeSelect: (CreateMode) -> Unit,
) {
    Row(
        modifier = modifier
            .clip(CaramelTheme.shape.xl)
            .background(CaramelTheme.color.fill.quaternary)
            .padding(CaramelTheme.spacing.xxs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CreateMode.entries.forEach {
            val icon = when (it) {
                CreateMode.MEMO -> painterResource(Resources.Icon.ic_memo_24)
                CreateMode.CALENDAR -> painterResource(Resources.Icon.ic_calendar_24)
            }
            ToggleIconButton(
                icon = icon,
                isSelected = createMode == it,
                onClick = { onCreateModeSelect(it) },
            )
        }

    }
}

@Composable
private fun ToggleIconButton(
    icon: Painter,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) CaramelTheme.color.fill.inverse else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = CaramelTheme.color.icon.primary,
            modifier = Modifier.size(18.dp)
        )
    }
}