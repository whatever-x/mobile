package com.whatever.caramel.core.ui.content

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import caramel.core.designsystem.generated.resources.Res
import caramel.core.designsystem.generated.resources.me
import caramel.core.designsystem.generated.resources.partner
import caramel.core.designsystem.generated.resources.us
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.stringResource

enum class ContentAssigneeUiModel {
    ME,
    PARTNER,
    US,
}

@Composable
fun ContentAssigneeChipRow(
    modifier: Modifier = Modifier,
    selectedAssigneeChip: ContentAssigneeUiModel,
    onAssigneeChipClick: (ContentAssigneeUiModel) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement =
            Arrangement.spacedBy(
                space = CaramelTheme.spacing.s,
            ),
    ) {
        ContentAssigneeUiModel.entries.forEach { contentAssignee ->
            val chipText =
                when (contentAssignee) {
                    ContentAssigneeUiModel.ME -> stringResource(resource = Res.string.me)
                    ContentAssigneeUiModel.PARTNER -> stringResource(resource = Res.string.partner)
                    ContentAssigneeUiModel.US -> stringResource(resource = Res.string.us)
                }

            ContentAssigneeChip(
                text = chipText,
                selected = selectedAssigneeChip == contentAssignee,
                onClick = { onAssigneeChipClick(contentAssignee) },
            )
        }
    }
}

@Composable
private fun ContentAssigneeChip(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) CaramelTheme.color.fill.primary else CaramelTheme.color.background.primary,
    )
    val textColor =
        if (selected) CaramelTheme.color.text.inverse else CaramelTheme.color.text.disabledPrimary
    val borderColor = if (selected) Color.Transparent else CaramelTheme.color.fill.disabledPrimary

    Box(
        modifier =
            modifier
                .widthIn(min = 56.dp)
                .clip(shape = CaramelTheme.shape.xl)
                .background(color = backgroundColor)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = CaramelTheme.shape.xl,
                ).clickable(
                    onClick = onClick,
                    indication = null,
                    interactionSource = null,
                ).padding(
                    vertical = CaramelTheme.spacing.s,
                    horizontal = CaramelTheme.spacing.m,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = CaramelTheme.typography.body4.regular,
            color = textColor,
        )
    }
}
