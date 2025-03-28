package com.whatever.caramel.feature.profile.create.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ProfileCreateTopBar(
    modifier: Modifier = Modifier,
    onClickBackButton: () -> Unit
) {
    Row(
        modifier = modifier
            .height(height = 52.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable(
                    onClick = onClickBackButton,
                    interactionSource = null,
                    indication = null
                ),
            painter = painterResource(resource = Resources.Icon.ic_arrow_left_24),
            tint = CaramelTheme.color.icon.primary,
            contentDescription = null
        )
    }
}