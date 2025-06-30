package com.whatever.caramel.feature.setting.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
internal fun SettingListButton(
    modifier: Modifier = Modifier,
    mainText: String,
    isChecked: Boolean,
    onClickTailButton: () -> Unit,
) {
    Row(
        modifier = modifier.height(height = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(weight = 1f),
            text = mainText,
            style = CaramelTheme.typography.body2.regular,
            color = CaramelTheme.color.text.primary,
        )

        val backgroundColor =
            if (isChecked) {
                CaramelTheme.color.fill.brand
            } else {
                CaramelTheme.color.fill.disabledPrimary
            }

        Box(
            modifier =
                modifier
                    .width(width = 51.dp)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(size = 100.dp),
                    ).clip(shape = RoundedCornerShape(size = 100.dp))
                    .clickable(
                        onClick = onClickTailButton,
                        interactionSource = null,
                        indication = null,
                    ).padding(all = 2.dp),
            contentAlignment = if (isChecked) Alignment.TopEnd else Alignment.TopStart,
        ) {
            Canvas(
                modifier = Modifier.size(size = 27.dp),
            ) {
                drawCircle(color = Color.White)
            }
        }
    }
}
