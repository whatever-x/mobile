package com.whatever.caramel.feature.setting.component.preview

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.setting.component.SettingProfileChangeBottomSheet
import com.whatever.caramel.feature.setting.component.SettingProfileChangeMenuItem

@Preview(showBackground = true)
@Composable
fun SettingBottomSheetMenuPreview() {
    CaramelTheme {
        SettingProfileChangeBottomSheet(
            navigateToProfileEditBrithDay = {},
            navigateToProfileEditNickName = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingProfileChangeMenuPreview() {
    CaramelTheme {
        SettingProfileChangeMenuItem(
            modifier = Modifier
                .padding(
                    horizontal = 20.dp,
                    vertical = 14.dp
                ),
            iconRes = Resources.Icon.ic_nickname_20,
            menu = "닉네임"
        ) { }
    }
}