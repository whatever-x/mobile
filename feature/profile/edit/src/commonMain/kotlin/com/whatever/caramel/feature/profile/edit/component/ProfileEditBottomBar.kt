package com.whatever.caramel.feature.profile.edit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
internal fun ProfileEditBottomBar(
    modifier: Modifier = Modifier,
    buttonEnabled: Boolean,
    buttonText: String,
    onClickButton: () -> Unit,
) {
    val buttonShape = CaramelTheme.shape.xl
    val buttonBackgroundColor =
        if (buttonEnabled) {
            CaramelTheme.color.fill.brand
        } else {
            CaramelTheme.color.fill.disabledPrimary
        }
    val textColor =
        if (buttonEnabled) {
            CaramelTheme.color.text.inverse
        } else {
            CaramelTheme.color.text.disabledPrimary
        }

    Box(
        modifier =
            modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier =
                Modifier
                    .height(height = 50.dp)
                    .fillMaxWidth()
                    .background(
                        color = buttonBackgroundColor,
                        shape = buttonShape,
                    )
                    .clip(shape = buttonShape)
                    .clickable(
                        enabled = buttonEnabled,
                        onClick = onClickButton,
                    ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = buttonText,
                style = CaramelTheme.typography.body1.bold,
                color = textColor,
            )
        }
    }
}
