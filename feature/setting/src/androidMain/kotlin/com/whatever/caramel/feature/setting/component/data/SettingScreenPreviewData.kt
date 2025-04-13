package com.whatever.caramel.feature.setting.component.data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.feature.setting.mvi.CoupleUser
import com.whatever.caramel.feature.setting.mvi.SettingState

internal data class SettingScreenPreviewData(
    val state: SettingState
)

internal class SettingScreenPreviewDataProvider :
    PreviewParameterProvider<SettingScreenPreviewData> {
    override val values: Sequence<SettingScreenPreviewData> = sequenceOf(
        SettingScreenPreviewData(state = SettingState()),
        SettingScreenPreviewData(
            state = SettingState(
                isLoading = false,
                isShowProfileChangeBottomSheet = true,
                startDateTimeMillisecond = 1743897600000L,
                myInfo = CoupleUser(
                    id = 123L,
                    nickname = "닉네임",
                    birthDayTimeMillisecond = 1743897600000L,
                    gender = Gender.MALE
                ),
                partnerInfo = CoupleUser(
                    id = 1234L,
                    nickname = "상대 닉네임",
                    birthDayTimeMillisecond = 1743897600000L,
                    gender = Gender.FEMALE
                )
            )
        ),
        SettingScreenPreviewData(
            state = SettingState(
                isLoading = false,
                startDateTimeMillisecond = 1743897600000L,
                myInfo = CoupleUser(
                    id = 123L,
                    nickname = "닉네임",
                    birthDayTimeMillisecond = 1743897600000L,
                    gender = Gender.MALE
                ),
                partnerInfo = CoupleUser(
                    id = 1234L,
                    nickname = "상대 닉네임",
                    birthDayTimeMillisecond = 1743897600000L,
                    gender = Gender.FEMALE
                )
            )
        ),
    )
}