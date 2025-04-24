package com.whatever.caramel.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

enum class CaramelButtonType {
    Enabled1,
    Enabled2,
    Disabled,
    ;
}

enum class CaramelButtonSize {
    Large,
    Medium,
    Small,
    ;
}

@Composable
fun CaramelButton(
    buttonType: CaramelButtonType,
    buttonSize: CaramelButtonSize,
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    val buttonHeight = when (buttonSize) {
        CaramelButtonSize.Large -> 50.dp
        CaramelButtonSize.Medium -> 42.dp
        CaramelButtonSize.Small -> 32.dp
    }
    val buttonBackgroundColor = when (buttonType) {
        CaramelButtonType.Enabled1 -> CaramelTheme.color.fill.brand
        CaramelButtonType.Enabled2 -> CaramelTheme.color.fill.quinary
        CaramelButtonType.Disabled -> CaramelTheme.color.fill.disabledPrimary
    }
    val buttonShape = when (buttonSize) {
        CaramelButtonSize.Large -> CaramelTheme.shape.xl
        CaramelButtonSize.Medium -> CaramelTheme.shape.xl
        CaramelButtonSize.Small -> CaramelTheme.shape.s
    }

    val textColor = when (buttonType) {
        CaramelButtonType.Enabled1 -> CaramelTheme.color.text.inverse
        CaramelButtonType.Enabled2 -> CaramelTheme.color.text.brand
        CaramelButtonType.Disabled -> CaramelTheme.color.text.disabledPrimary
    }
    val textStyle = when (buttonSize) {
        CaramelButtonSize.Large -> CaramelTheme.typography.body1.bold
        CaramelButtonSize.Medium -> CaramelTheme.typography.body3.bold
        CaramelButtonSize.Small -> CaramelTheme.typography.label1.bold
    }

    Box(
        modifier = modifier
            .height(height = buttonHeight)
            .background(
                color = buttonBackgroundColor,
                shape = buttonShape
            )
            .clip(shape = buttonShape)
            .clickable(
                enabled = buttonType != CaramelButtonType.Disabled,
                onClick = onClick
            )
            .padding(horizontal = CaramelTheme.spacing.l),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}