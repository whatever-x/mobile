package com.whatever.caramel.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun CaramelBottomNavigationWithTrailingButton(
    modifier: Modifier = Modifier,
    currentItem: BottomNavItem,
    onClickNavItem: (BottomNavItem) -> Unit,
    trailingButton: @Composable () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(height = 56.dp)
                .background(color = CaramelTheme.color.background.tertiary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        BottomNavItem.entries.forEach { bottomNavItem ->
            CaramelNavigationItem(
                icon = bottomNavItem.icon,
                text = bottomNavItem.text,
                isSelected = currentItem == bottomNavItem,
                onClick = { onClickNavItem(bottomNavItem) },
            )
        }

        trailingButton.invoke()
    }
}

enum class BottomNavItem(
    val text: String,
    val icon: DrawableResource,
) {
    HOME(text = "홈", icon = Resources.Icon.ic_home_24),
    CALENDAR(text = "캘린더", icon = Resources.Icon.ic_calendar_24),
    MEMO(text = "메모", icon = Resources.Icon.ic_memo_24),
}

@Composable
fun CaramelNavigationItem(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .clickable(
                    enabled = !isSelected,
                    onClick = onClick,
                    indication = null,
                    interactionSource = null,
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(resource = icon),
            tint =
                if (isSelected) {
                    CaramelTheme.color.icon.primary
                } else {
                    CaramelTheme.color.icon.disabledPrimary
                },
            contentDescription = null,
        )

        Text(
            text = text,
            style = CaramelTheme.typography.label3.regular,
            color =
                if (isSelected) {
                    CaramelTheme.color.text.primary
                } else {
                    CaramelTheme.color.text.tertiary
                },
        )
    }
}

@Composable
fun CaramelNavItemCreateButton(
    modifier: Modifier = Modifier,
    onClickButton: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .size(40.dp)
                .background(
                    color = CaramelTheme.color.fill.quinary,
                    shape = CaramelTheme.shape.s,
                ).clip(shape = CaramelTheme.shape.s)
                .clickable(
                    onClick = onClickButton,
                    indication = null,
                    interactionSource = null,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(resource = Resources.Icon.ic_circle_plus_solid_24),
            tint = CaramelTheme.color.icon.brand,
            contentDescription = null,
        )
    }
}
