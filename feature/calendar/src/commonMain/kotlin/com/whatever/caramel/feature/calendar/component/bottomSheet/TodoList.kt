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
internal interface CaramelBottomTodoScope {
    val id: Long
    val title: String
    val description: String
    val url: String?
    val assignee: ContentAssignee
    val onClickUrl: (String?) -> Unit
    val onClickTodo: (Long) -> Unit
}

internal class CaramelDefaultBottomTodoScope(
    override val id: Long,
    override val title: String,
    override val description: String,
    override val url: String?,
    override val assignee: ContentAssignee,
    override val onClickUrl: (String?) -> Unit,
    override val onClickTodo: (Long) -> Unit,
) : CaramelBottomTodoScope

@Composable
internal fun BottomSheetTodoItem(
    id: Long,
    title: String,
    description: String,
    url: String? = null,
    assignee: ContentAssignee,
    onClickTodo: (Long) -> Unit = {},
    onClickUrl: (String?) -> Unit = {},
    content: @Composable CaramelBottomTodoScope.() -> Unit,
) {
    val scope =
        remember(
            id,
            title,
            description,
            url,
            onClickTodo,
            onClickUrl,
        ) {
            CaramelDefaultBottomTodoScope(
                id = id,
                title = title,
                description = description,
                url = url,
                onClickTodo = onClickTodo,
                onClickUrl = onClickUrl,
                assignee = assignee,
            )
        }
    scope.content()
}

@Composable
internal fun CaramelBottomTodoScope.DefaultBottomSheetTodoItem(modifier: Modifier = Modifier) {
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
        TodoRole()
        Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.s))
        TodoTitle()
        if (hasAll) {
            Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.xxs))
            TodoDescription()
        }
        if (hasUrl) {
            TodoUrl()
        }
    }
}

@Composable
internal fun CaramelBottomTodoScope.TodoRole(modifier: Modifier = Modifier) {
    val (roleText, roleTextColor) =
        when (this.assignee) {
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
                    onClick = { onClickTodo(id) },
                ),
        text = stringResource(roleText),
        style = CaramelTheme.typography.body3.bold,
        color = roleTextColor,
    )
}

@Composable
internal fun CaramelBottomTodoScope.TodoTitle(modifier: Modifier = Modifier) {
    val mainText = this.title.ifEmpty { description }
    Text(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = { onClickTodo(id) },
                ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        text = mainText,
        style = CaramelTheme.typography.body3.regular,
        color = CaramelTheme.color.text.primary,
    )
}

@Composable
internal fun CaramelBottomTodoScope.TodoDescription(modifier: Modifier = Modifier) {
    val descriptionText = this.description.ifEmpty { return }
    Text(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = { onClickTodo(id) },
                ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        text = descriptionText,
        style = CaramelTheme.typography.body3.regular,
        color = CaramelTheme.color.text.primary,
    )
}

@Composable
internal fun CaramelBottomTodoScope.TodoUrl(modifier: Modifier = Modifier) {
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
            horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.xs),
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
