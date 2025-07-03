package com.whatever.caramel.feature.setting.component.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.setting.SettingScreen
import com.whatever.caramel.feature.setting.component.data.SettingScreenPreviewData
import com.whatever.caramel.feature.setting.component.data.SettingScreenPreviewDataProvider

@Preview
@Composable
private fun SettingScreenPreview(
    @PreviewParameter(SettingScreenPreviewDataProvider::class) data: SettingScreenPreviewData,
) {
    CaramelTheme {
        SettingScreen(
            state = data.state,
        ) { }
    }
}
