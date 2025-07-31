package com.whatever.caramel.feature.home.components.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import caramel.feature.home.generated.resources.Res
import caramel.feature.home.generated.resources.me
import caramel.feature.home.generated.resources.partner
import caramel.feature.home.generated.resources.us
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ContentAssigneeChip(assignee: ContentAssignee) {
    val chipBackgroundColor =
        when (assignee) {
            ContentAssignee.ME -> CaramelTheme.color.fill.accent1
            ContentAssignee.PARTNER -> CaramelTheme.color.fill.secondary
            ContentAssignee.US -> CaramelTheme.color.fill.brand
        }
    val chipText =
        when (assignee) {
            ContentAssignee.ME -> stringResource(resource = Res.string.me)
            ContentAssignee.PARTNER -> stringResource(resource = Res.string.partner)
            ContentAssignee.US -> stringResource(resource = Res.string.us)
        }

    Box(
        modifier =
            Modifier
                .size(
                    width = 30.dp,
                    height = 20.dp,
                ).background(
                    color = chipBackgroundColor,
                    shape = CaramelTheme.shape.xs,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = chipText,
            style = CaramelTheme.typography.label2.bold,
            color = CaramelTheme.color.text.inverse,
        )
    }
}
