package com.whatever.caramel.feature.profile.edit.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.ui.picker.DateUiState
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditState
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditType

internal data class ProfileEditPreviewData(
    val state: ProfileEditState
)

internal class ProfileEditPreviewDataProvider : PreviewParameterProvider<ProfileEditPreviewData> {
    override val values: Sequence<ProfileEditPreviewData>
        get() = sequenceOf(
            ProfileEditPreviewData(
                state = ProfileEditState(
                    editUiType = ProfileEditType.BIRTHDAY,
                    birthDay = DateUiState(year = 2025, month = 4, day = 19),
                )
            ),
            ProfileEditPreviewData(
                state = ProfileEditState(
                    editUiType = ProfileEditType.START_DATE,
                    startDate = DateUiState(year = 2025, month = 4, day = 19),
                )
            ),
            ProfileEditPreviewData(
                state = ProfileEditState(
                    editUiType = ProfileEditType.NICKNAME,
                    nickName = "유승우"
                )
            ),
            ProfileEditPreviewData(
                state = ProfileEditState(
                    editUiType = ProfileEditType.NICKNAME,
                    nickName = ""
                )
            )
        )
}