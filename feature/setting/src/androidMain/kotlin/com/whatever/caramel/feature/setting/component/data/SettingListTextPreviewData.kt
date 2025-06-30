package com.whatever.caramel.feature.setting.component.data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal data class SettingListTextPreviewData(
    val text: String,
    val tailText: String?,
)

internal class SettingListTextPreviewDataProvider :
    PreviewParameterProvider<SettingListTextPreviewData> {
    override val values: Sequence<SettingListTextPreviewData> =
        sequenceOf(
            SettingListTextPreviewData(
                text = "List1",
                tailText = null,
            ),
            SettingListTextPreviewData(
                text = "List2",
                tailText = "TailText",
            ),
        )
}
