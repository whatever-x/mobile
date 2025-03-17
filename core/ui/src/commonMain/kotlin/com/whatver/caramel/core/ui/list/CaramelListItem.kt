package com.whatver.caramel.core.ui.list


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.vectorResource

@Composable
fun CaramelListItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (() -> Unit)? = null,
    content: CaramelListItemScope.() -> Unit = {}
) {
    val scope = DefaultCaramelListItemScope().apply(content)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = CaramelTheme.color.background.primary)
            .clickable {
                onClick?.invoke()
            }
            .padding(vertical = 14.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        scope.leadingIcon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.padding(end = 10.dp)
            )
        }

        Text(
            text = text,
            style = CaramelTheme.typography.body2.regular,
            color = CaramelTheme.color.text.primary,
            modifier = Modifier.weight(1f),
        )

        scope.trailingText?.let {
            Text(
                text = it,
                style = CaramelTheme.typography.body3.regular,
                color = CaramelTheme.color.text.accent,
            )
        }

        if (scope.hasTrailingArrow) {
            Icon(
                imageVector = vectorResource(Resources.Icon.arrow_right),
                contentDescription = null
            )
        }
    }
}
