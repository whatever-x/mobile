package com.whatever.caramel.feature.content.detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import caramel.feature.content.detail.generated.resources.Res
import caramel.feature.content.detail.generated.resources.both
import caramel.feature.content.detail.generated.resources.memo
import caramel.feature.content.detail.generated.resources.my
import caramel.feature.content.detail.generated.resources.partner
import caramel.feature.content.detail.generated.resources.schedule
import com.whatever.caramel.core.designsystem.components.shimmer
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentType
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ContentAssigneeText(
    modifier: Modifier,
    isLoading: Boolean,
    contentType: ContentType,
    contentAssignee: ContentAssignee,
) {
    if (isLoading) {
        ContentAssigneeTextSkeleton(modifier = modifier)
    } else {
        val (assigneeTextRes, assigneeTextColor) =
            when (contentAssignee) {
                ContentAssignee.ME -> Res.string.my to CaramelTheme.color.text.labelAccent4
                ContentAssignee.PARTNER -> Res.string.partner to CaramelTheme.color.text.primary
                ContentAssignee.US -> Res.string.both to CaramelTheme.color.text.brand
            }

        val contentTypeRes =
            when (contentType) {
                ContentType.MEMO -> Res.string.memo
                ContentType.CALENDAR -> Res.string.schedule
            }
        Text(
            modifier = modifier,
            text = stringResource(assigneeTextRes) + " " + stringResource(contentTypeRes),
            color = assigneeTextColor,
            style = CaramelTheme.typography.body2.bold,
        )
    }
}

@Composable
internal fun ContentAssigneeTextSkeleton(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .size(width = 60.dp, height = 23.dp)
                .shimmer(shape = CaramelTheme.shape.xs),
    )
}
