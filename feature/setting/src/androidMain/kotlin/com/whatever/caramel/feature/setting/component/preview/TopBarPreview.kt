package com.whatever.caramel.feature.setting.component.preview

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.setting.component.SettingTopBar
import com.whatever.caramel.feature.setting.component.data.SettingTopBarPreviewData
import com.whatever.caramel.feature.setting.component.data.SettingTopBarPreviewDataProvider
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
internal fun SettingTopBarPreview(
    @PreviewParameter(SettingTopBarPreviewDataProvider::class) data: SettingTopBarPreviewData
) {
    CaramelTheme {
        SettingTopBar(
            text = data.text,
            leadingContent = if (data.leadingIcon != null) {
                {
                    Icon(
                        modifier = Modifier
                            .padding(vertical = 14.dp, horizontal = 16.dp),
                        contentDescription = null,
                        painter = painterResource(data.leadingIcon)
                    )
                }
            } else {
                null
            },
            tailingContent = if (data.tailingIcon != null) {
                {
                    Icon(
                        modifier = Modifier
                            .padding(vertical = 14.dp, horizontal = 16.dp),
                        contentDescription = null,
                        painter = painterResource(data.tailingIcon)
                    )
                }
            } else {
                null
            }
        )
    }
}
