package com.whatever.caramel.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.home.mvi.TodoState
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

internal fun LazyListScope.Todo(
    todoList: ImmutableList<TodoState>,
    isSetAnniversary: Boolean,
    onClickAnniversaryNudgeCard: () -> Unit,
    onClickTodoItem: (todoContentId: Long) -> Unit,
    onClickEmptyTodo: () -> Unit
) {
    item(key = "Todos") {
        Column(
            modifier = Modifier
                .padding(
                    start = CaramelTheme.spacing.xl,
                    end = CaramelTheme.spacing.xl,
                    bottom = CaramelTheme.spacing.xl,
                ),
            verticalArrangement = Arrangement.spacedBy(
                space = CaramelTheme.spacing.s
            )
        ) {
            if (!isSetAnniversary) {
                NudgeCard(
                    text = "커플 시작일을 입력해주세요.\n기념일을 바로 알 수 있어요!",
                    iconResource = Resources.Icon.ic_arrow_right_16,
                    onClick = onClickAnniversaryNudgeCard
                )
            }

            TodoList(
                modifier = Modifier
                    .background(
                        color = CaramelTheme.color.fill.quaternary,
                        shape = CaramelTheme.shape.l
                    )
                    .padding(
                        bottom = CaramelTheme.spacing.m,
                        top = CaramelTheme.spacing.l,
                        start = CaramelTheme.spacing.l,
                        end = CaramelTheme.spacing.l
                    ),
                todoList = todoList,
                onClickTodoItem = onClickTodoItem,
                onClickEmptyTodo = onClickEmptyTodo
            )
        }
    }
}

@Composable
private fun TodoList(
    modifier: Modifier = Modifier,
    todoList: ImmutableList<TodoState>,
    onClickTodoItem: (todoContentId: Long) -> Unit,
    onClickEmptyTodo: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "오늘 일정",
            style = CaramelTheme.typography.heading3,
            color = CaramelTheme.color.text.primary
        )

        Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.s))

        if (todoList.isEmpty()) {
            EmptyTodo(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = onClickEmptyTodo,
                        interactionSource = null,
                        indication = null
                    )
                    .padding(vertical = CaramelTheme.spacing.s),
            )
        } else {
            todoList.forEachIndexed { index, todo ->
                TodoItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = { onClickTodoItem(todo.id) },
                            interactionSource = null,
                            indication = null
                        )
                        .padding(
                            vertical = CaramelTheme.spacing.s,
                        ),
                    title = todo.title
                )

                if (index < todoList.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = CaramelTheme.spacing.xs),
                        thickness = 1.dp,
                        color = CaramelTheme.color.divider.tertiary
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
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = CaramelTheme.spacing.m
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(weight = 1f),
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = CaramelTheme.typography.body3.regular,
            color = CaramelTheme.color.text.primary
        )

        Icon(
            painter = painterResource(resource = Resources.Icon.ic_arrow_right_16),
            tint = CaramelTheme.color.icon.tertiary,
            contentDescription = null
        )
    }
}

@Composable
private fun EmptyTodo(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "오늘 할 일을 등록해 주세요",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = CaramelTheme.typography.body3.regular,
            color = CaramelTheme.color.text.placeholder
        )

        Icon(
            painter = painterResource(resource = Resources.Icon.ic_plus_16),
            tint = CaramelTheme.color.icon.tertiary,
            contentDescription = null
        )
    }
}

@Composable
private fun NudgeCard(
    modifier: Modifier = Modifier,
    text: String,
    iconResource: DrawableResource,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = CaramelTheme.color.fill.quaternary,
                shape = CaramelTheme.shape.l
            )
            .clip(shape = CaramelTheme.shape.l)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = null
            )
            .padding(all = CaramelTheme.spacing.l),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = CaramelTheme.typography.body3.bold,
            color = CaramelTheme.color.text.primary
        )

        Icon(
            painter = painterResource(resource = iconResource),
            tint = CaramelTheme.color.icon.tertiary,
            contentDescription = null,
        )
    }
}