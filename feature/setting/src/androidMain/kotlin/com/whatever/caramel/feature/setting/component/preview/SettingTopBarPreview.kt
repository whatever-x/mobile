package com.whatever.caramel.feature.setting.component.preview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.setting.component.SettingTopBar

@Preview(showBackground = true)
@Composable
private fun SettingTopBarPreview() {
    CaramelTheme {
        SettingTopBar(
            modifier = Modifier
                .fillMaxWidth(),
            appbarText = "설정",
        ) { }
    }
}