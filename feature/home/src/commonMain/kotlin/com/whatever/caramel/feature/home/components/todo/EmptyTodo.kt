package com.whatever.caramel.feature.home.components.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun EmptyTodo(modifier: Modifier = Modifier) {
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
