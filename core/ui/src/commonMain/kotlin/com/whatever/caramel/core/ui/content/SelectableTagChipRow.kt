package com.whatever.caramel.core.ui.content

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import kotlinx.collections.immutable.ImmutableList

data class TagChip(
    val id: Long,
    val label: String,
)

@Composable
fun SelectableTagChipRow(
    modifier: Modifier = Modifier,
    tagChips: ImmutableList<TagChip>,
    selectedTagChips: ImmutableList<TagChip>,
    onTagChipClick: (TagChip) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.s),
        contentPadding = PaddingValues(horizontal = CaramelTheme.spacing.xl),
    ) {
        items(tagChips) { tag ->
            SelectableTagChip(
                text = tag.label,
                selected = selectedTagChips.contains(tag),
                onClick = {
                    onTagChipClick(tag)
                },
            )
        }
    }
}

@Composable
private fun SelectableTagChip(
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
    val borderColor = if (selected) Color.Transparent else CaramelTheme.color.text.disabledPrimary

    Box(
        modifier =
            modifier
                .height(34.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(backgroundColor)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(24.dp),
                ).clickable(onClick = onClick)
                .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = CaramelTheme.typography.body4.regular,
            color = textColor,
        )
    }
}
