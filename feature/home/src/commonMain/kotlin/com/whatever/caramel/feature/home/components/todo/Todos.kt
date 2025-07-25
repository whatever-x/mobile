package com.whatever.caramel.feature.home.components.todo

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import caramel.feature.home.generated.resources.Res
import caramel.feature.home.generated.resources.start_couple_date_guid
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.content.ContentRole
import com.whatever.caramel.feature.home.mvi.TodoItem
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

internal fun LazyListScope.todo(
    todoList: ImmutableList<TodoItem>,
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
    todoList: ImmutableList<TodoItem>,
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
            horizontalArrangement =
                Arrangement.spacedBy(
                    space = CaramelTheme.spacing.s,
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
