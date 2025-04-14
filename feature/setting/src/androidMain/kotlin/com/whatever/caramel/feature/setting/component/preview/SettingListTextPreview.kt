package com.whatever.caramel.feature.setting.component.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.setting.component.SettingListText
import com.whatever.caramel.feature.setting.component.data.SettingListTextPreviewData
import com.whatever.caramel.feature.setting.component.data.SettingListTextPreviewDataProvider

@Preview(showBackground = true)
@Composable
private fun SettingListTextPreview(
    @PreviewParameter(SettingListTextPreviewDataProvider::class) data: SettingListTextPreviewData
) {
    CaramelTheme {
        SettingListText(
            mainText = data.text,
            tailText = data.tailText,
            mainTextColor = CaramelTheme.color.text.primary,
            onClickListItem = {},
            onClickTailText = null
        )

        SettingListText(
            mainText = data.text,
            mainTextColor = CaramelTheme.color.text.tertiary,
            onClickListItem = {}
        )
    }
}