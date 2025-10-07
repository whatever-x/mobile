package com.whatever.caramel.feature.calendar.component.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import caramel.feature.calendar.generated.resources.Res
import caramel.feature.calendar.generated.resources.both_schedule
import caramel.feature.calendar.generated.resources.my_schedule
import caramel.feature.calendar.generated.resources.partner_schedule
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Stable
internal interface CaramelBottomSheetScheduleScope {
    val id: Long
    val title: String
    val description: String
    val url: String?
    val contentAssignee: ContentAssignee
    val onClickUrl: (String?) -> Unit
    val onClickSchedule: (Long) -> Unit
}

internal class CaramelDefaultBottomSheetScheduleScope(
    override val id: Long,
    override val title: String,
    override val description: String,
    override val url: String?,
    override val contentAssignee: ContentAssignee,
    override val onClickUrl: (String?) -> Unit,
    override val onClickSchedule: (Long) -> Unit,
) : CaramelBottomSheetScheduleScope

@Composable
internal fun BottomSheetScheduleItem(
    id: Long,
    title: String,
    description: String,
    url: String? = null,
    contentAssignee: ContentAssignee,
    onClickSchedule: (Long) -> Unit = {},
    onClickUrl: (String?) -> Unit = {},
    content: @Composable CaramelBottomSheetScheduleScope.() -> Unit,
) {
    val scope =
        remember(
            id,
            title,
            description,
            url,
            onClickSchedule,
            onClickUrl,
        ) {
            CaramelDefaultBottomSheetScheduleScope(
                id = id,
                title = title,
                description = description,
                url = url,
                onClickSchedule = onClickSchedule,
                onClickUrl = onClickUrl,
                contentAssignee = contentAssignee,
            )
        }
    scope.content()
}

@Composable
internal fun CaramelBottomSheetScheduleScope.DefaultBottomSheetScheduleItem(modifier: Modifier = Modifier) {
    val hasAll = this.title.isNotEmpty() && this.description.isNotEmpty()
    val hasUrl = !this.url.isNullOrEmpty()

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = Color(color = 0xFFF7F2EC),
                    shape = CaramelTheme.shape.m,
                ).padding(all = CaramelTheme.spacing.l),
    ) {
        ScheduleAssignee()
        Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.s))
        ScheduleTitle()
        if (hasAll) {
            Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.xxs))
            ScheduleDescription()
        }
        if (hasUrl) {
            ScheduleUrl()
        }
    }
}

@Composable
internal fun CaramelBottomSheetScheduleScope.ScheduleAssignee(modifier: Modifier = Modifier) {
    val (assigneeTextRes, assigneeTextColor) =
        when (this.contentAssignee) {
            ContentAssignee.ME -> Res.string.my_schedule to CaramelTheme.color.text.labelAccent4
            ContentAssignee.PARTNER -> Res.string.partner_schedule to CaramelTheme.color.text.primary
            ContentAssignee.US -> Res.string.both_schedule to CaramelTheme.color.text.brand
        }

    Text(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = { onClickSchedule(id) },
                ),
        text = stringResource(assigneeTextRes),
        style = CaramelTheme.typography.body3.bold,
        color = assigneeTextColor,
    )
}

@Composable
internal fun CaramelBottomSheetScheduleScope.ScheduleTitle(modifier: Modifier = Modifier) {
    val mainText = this.title.ifEmpty { description }
    Text(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = { onClickSchedule(id) },
                ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        text = mainText,
        style = CaramelTheme.typography.body3.regular,
        color = CaramelTheme.color.text.primary,
    )
}

@Composable
internal fun CaramelBottomSheetScheduleScope.ScheduleDescription(modifier: Modifier = Modifier) {
    val descriptionText = this.description.ifEmpty { return }
    Text(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = { onClickSchedule(id) },
                ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        text = descriptionText,
        style = CaramelTheme.typography.body3.regular,
        color = CaramelTheme.color.text.primary,
    )
}

@Composable
internal fun CaramelBottomSheetScheduleScope.ScheduleUrl(modifier: Modifier = Modifier) {
    val urlText = this.url ?: return

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = CaramelTheme.spacing.m),
            color = CaramelTheme.color.divider.tertiary,
        )
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { onClickUrl(urlText) },
                    ),
            horizontalArrangement =
                Arrangement.spacedBy(
                    CaramelTheme.spacing.xs,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(Resources.Icon.ic_link_16),
                tint = CaramelTheme.color.icon.secondary,
                contentDescription = null,
            )
            Text(
                modifier =
                    Modifier
                        .weight(1f),
                text = urlText,
                style = CaramelTheme.typography.body4.regular,
                color = CaramelTheme.color.text.secondary,
            )
            Icon(
                painter = painterResource(Resources.Icon.ic_arrow_right_14),
                tint = CaramelTheme.color.icon.tertiary,
                contentDescription = null,
            )
        }
    }
}
