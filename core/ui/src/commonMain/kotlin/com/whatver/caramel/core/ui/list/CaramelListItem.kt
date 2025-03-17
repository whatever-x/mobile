package com.whatver.caramel.core.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
fun CaramelListItem(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .background(color = CaramelTheme.color.background.primary)
            .clickable {
                onClick?.invoke()
            }
            .padding(vertical = 14.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leading?.invoke()
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            content()
        }
        trailing?.invoke()
    }
}