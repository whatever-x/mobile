package com.whatever.caramel.feature.memo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.entity.Tag

@Composable
internal fun TagChip(
    modifier: Modifier = Modifier,
    tag: Tag,
    isSelected: Boolean,
    onClickChip: (Tag) -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = if (isSelected) CaramelTheme.color.fill.primary else CaramelTheme.color.background.tertiary,
                shape = CaramelTheme.shape.xl
            )
            .padding(horizontal = CaramelTheme.spacing.m, vertical = CaramelTheme.spacing.s)
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = { onClickChip(tag) }
            )
    ) {
        Text(
            text = tag.label,
            style = CaramelTheme.typography.body4.regular,
            color = if (isSelected) CaramelTheme.color.text.inverse else CaramelTheme.color.text.primary
        )
    }
}
