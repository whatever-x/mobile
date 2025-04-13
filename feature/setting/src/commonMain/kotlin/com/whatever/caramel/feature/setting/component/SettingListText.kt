package com.whatever.caramel.feature.setting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
internal fun SettingListText(
    modifier: Modifier = Modifier,
    mainText: String,
    mainTextColor: Color,
    tailText: String? = null,
    onClick: () -> Unit,
    onTailClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .padding(
                vertical = 14.dp,
                horizontal = 20.dp
            )
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = onClick
                )
        ) {
            Text(
                text = mainText,
                style = CaramelTheme.typography.body2.regular,
                color = mainTextColor
            )
        }
        if (tailText != null) {
            Text(
                modifier = Modifier
                    .padding(
                        start = 16.dp
                    )
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { onTailClick?.invoke() }
                    ),
                text = tailText,
                style = CaramelTheme.typography.body3.regular,
                color = CaramelTheme.color.text.brand,
            )
        }
    }
}