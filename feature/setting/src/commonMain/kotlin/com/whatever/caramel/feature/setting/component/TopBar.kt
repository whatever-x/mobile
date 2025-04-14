package com.whatever.caramel.feature.setting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun SettingTopBar(
    modifier: Modifier = Modifier,
    appbarText: String,
    onClickBackButton: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(52.dp)
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterStart)
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = onClickBackButton
                ),
            painter = painterResource(Resources.Icon.ic_arrow_left_24),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = appbarText,
            style = CaramelTheme.typography.heading2,
            color = CaramelTheme.color.text.primary
        )
    }
}