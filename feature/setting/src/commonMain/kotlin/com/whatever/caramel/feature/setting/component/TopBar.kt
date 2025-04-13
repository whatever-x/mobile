package com.whatever.caramel.feature.setting.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
internal fun SettingTopBar(
    modifier: Modifier = Modifier,
    text: String? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    tailingContent: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = modifier
            .height(52.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            leadingContent?.invoke()
        }
        if (text != null) {
            Text(
                text = text,
                modifier = Modifier
                    .align(Alignment.Center),
                style = CaramelTheme.typography.heading3,
                color = CaramelTheme.color.text.primary
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            tailingContent?.invoke()
        }
    }
}
