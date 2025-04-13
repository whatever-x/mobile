package com.whatever.caramel.feature.setting.component.data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.designsystem.foundations.Resources
import org.jetbrains.compose.resources.DrawableResource

internal data class SettingTopBarPreviewData(
    val text: String? = null,
    val tailingIcon: DrawableResource? = null,
    val leadingIcon: DrawableResource? = null
)

internal class SettingTopBarPreviewDataProvider :
    PreviewParameterProvider<SettingTopBarPreviewData> {
    override val values: Sequence<SettingTopBarPreviewData>
        get() = sequenceOf(
            SettingTopBarPreviewData(
                tailingIcon = Resources.Icon.ic_cancel_24
            ),
            SettingTopBarPreviewData(
                text = "설정",
                leadingIcon = Resources.Icon.ic_arrow_left_24
            ),
            SettingTopBarPreviewData(
                leadingIcon = Resources.Icon.ic_cancel_24
            )
        )
}