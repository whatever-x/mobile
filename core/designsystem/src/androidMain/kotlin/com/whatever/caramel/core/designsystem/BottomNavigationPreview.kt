package com.whatever.caramel.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.components.BottomNavItem
import com.whatever.caramel.core.designsystem.components.CaramelBottomNavigationWithTrailingButton
import com.whatever.caramel.core.designsystem.components.CaramelNavItemCreateButton
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Preview(showBackground = true)
@Composable
private fun BottomNavigationPreview() {
    CaramelTheme {
        CaramelBottomNavigationWithTrailingButton(
            currentItem = BottomNavItem.HOME,
            onClickNavItem = {},
            trailingButton = {
                CaramelNavItemCreateButton { }
            },
        )
    }
}
