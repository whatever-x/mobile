package com.whatever.caramel.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import caramel.feature.home.generated.resources.Res
import caramel.feature.home.generated.resources.both
import caramel.feature.home.generated.resources.my
import caramel.feature.home.generated.resources.partner
import caramel.feature.home.generated.resources.start_couple_date_guid
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.content.ContentRole
import com.whatever.caramel.feature.home.mvi.TodoState
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

internal fun LazyListScope.todo(
    todoList: ImmutableList<TodoState>,
    isSetAnniversary: Boolean,
    onClickAnniversaryNudgeCard: () -> Unit,
    onClickTodoItem: (todoContentId: Long) -> Unit,
    onClickEmptyTodo: () -> Unit,
) {
    item(key = "Todos") {
        Column(
            modifier =
                Modifier
                    .padding(
                        start = CaramelTheme.spacing.xl,
                        end = CaramelTheme.spacing.xl,
                        bottom = CaramelTheme.spacing.xl,
                    ),
            verticalArrangement =
                Arrangement.spacedBy(
                    space = CaramelTheme.spacing.s,
                ),
        ) {
            if (!isSetAnniversary) {
                NudgeCard(
                    text = stringResource(resource = Res.string.start_couple_date_guid),
                    iconResource = Resources.Icon.ic_arrow_right_16,
                    onClick = onClickAnniversaryNudgeCard,
                )
            }

            TodoList(
                modifier =
                    Modifier
                        .background(
                            color = CaramelTheme.color.fill.quaternary,
                            shape = CaramelTheme.shape.l,
                        ).padding(
                            bottom = CaramelTheme.spacing.m,
                            top = CaramelTheme.spacing.l,
                            start = CaramelTheme.spacing.l,
                            end = CaramelTheme.spacing.l,
                        ),
                todoList = todoList,
                onClickTodoItem = onClickTodoItem,
                onClickEmptyTodo = onClickEmptyTodo,
            )
        }
    }
}

@Composable
private fun TodoList(
    modifier: Modifier = Modifier,
    todoList: ImmutableList<TodoState>,
    onClickTodoItem: (todoContentId: Long) -> Unit,
    onClickEmptyTodo: () -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "오늘 일정",
            style = CaramelTheme.typography.heading3,
            color = CaramelTheme.color.text.primary,
        )

        Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.xs))

        if (todoList.isEmpty()) {
            EmptyTodo(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = onClickEmptyTodo,
                            interactionSource = null,
                            indication = null,
                        ).padding(vertical = CaramelTheme.spacing.s),
            )
        } else {
            todoList.forEachIndexed { index, todo ->
                TodoItem(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = { onClickTodoItem(todo.id) },
                                interactionSource = null,
                                indication = null,
                            ).padding(
                                vertical = CaramelTheme.spacing.s,
                            ),
                    title = todo.title,
                    role = todo.role,
                )

                if (index < todoList.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = CaramelTheme.spacing.xs),
                        thickness = 1.dp,
                        color = CaramelTheme.color.divider.tertiary,
                    )
                }
            }
        }
    }
}

@Composable
private fun TodoItem(
    modifier: Modifier = Modifier,
    title: String,
    role: ContentRole,
) {
    Row(
        modifier = modifier,
        horizontalArrangement =
            Arrangement.spacedBy(
                space = CaramelTheme.spacing.m,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(weight = 1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = CaramelTheme.spacing.s
            ),
        ) {
            ContentRoleChip(role = role)

            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = CaramelTheme.typography.body3.regular,
                color = CaramelTheme.color.text.primary,
            )
        }

        Icon(
            painter = painterResource(resource = Resources.Icon.ic_arrow_right_16),
            tint = CaramelTheme.color.icon.tertiary,
            contentDescription = null,
        )
    }
}

@Composable
private fun EmptyTodo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "오늘 할 일을 등록해 주세요",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = CaramelTheme.typography.body3.regular,
            color = CaramelTheme.color.text.placeholder,
        )

        Icon(
            painter = painterResource(resource = Resources.Icon.ic_plus_16),
            tint = CaramelTheme.color.icon.tertiary,
            contentDescription = null,
        )
    }
}

@Composable
private fun NudgeCard(
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

@Composable
private fun ContentRoleChip(
    role: ContentRole
) {
    val chipBackgroundColor = when (role) {
        ContentRole.NONE -> Color.Black
        ContentRole.MY -> Color(0xFF009B08) // @ham2174 FIXME : 컬러 토큰 지정시 변경
        ContentRole.PARTNER -> CaramelTheme.color.fill.secondary
        ContentRole.BOTH -> CaramelTheme.color.fill.brand
    }
    val chipText = when (role) {
        ContentRole.NONE -> ""
        ContentRole.MY -> stringResource(resource = Res.string.my)
        ContentRole.PARTNER -> stringResource(resource = Res.string.partner)
        ContentRole.BOTH -> stringResource(resource = Res.string.both)
    }

    Box(
        modifier = Modifier
            .size(
                width = 30.dp,
                height = 20.dp
            )
            .background(
                color = chipBackgroundColor,
                shape = CaramelTheme.shape.xs
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = chipText,
            style = CaramelTheme.typography.label2.bold,
            color = CaramelTheme.color.text.inverse
        )
    }
}
















