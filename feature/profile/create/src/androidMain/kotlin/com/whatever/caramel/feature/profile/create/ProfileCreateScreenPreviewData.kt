package com.whatever.caramel.feature.profile.create

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.feature.profile.create.mvi.Birthday
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateState
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateStep

internal data class ProfileCreateScreenPreviewData(
    val state: ProfileCreateState
)

internal class ProfileCreateScreenPreviewProvider :
    PreviewParameterProvider<ProfileCreateScreenPreviewData> {
    override val values: Sequence<ProfileCreateScreenPreviewData> = sequenceOf(
        ProfileCreateScreenPreviewData(
            state = ProfileCreateState(
                nickname = "닉네임 있는 경우",
                currentStep = ProfileCreateStep.NICKNAME
            )
        ),
        ProfileCreateScreenPreviewData(
            state = ProfileCreateState(
                nickname = "",
                currentStep = ProfileCreateStep.NICKNAME
            )
        ),
        ProfileCreateScreenPreviewData(
            state = ProfileCreateState(
                gender = Gender.IDLE,
                currentStep = ProfileCreateStep.GENDER
            )
        ),
        ProfileCreateScreenPreviewData(
            state = ProfileCreateState(
                gender = Gender.MALE,
                currentStep = ProfileCreateStep.GENDER
            )
        ),
        ProfileCreateScreenPreviewData(
            state = ProfileCreateState(
                gender = Gender.FEMALE,
                currentStep = ProfileCreateStep.GENDER
            )
        ),
        ProfileCreateScreenPreviewData(
            state = ProfileCreateState(
                birthday = Birthday.currentDay(), // @ham2174 TODO : Birthday 피커 구현시 추가
                currentStep = ProfileCreateStep.BIRTHDAY
            )
        ),
        ProfileCreateScreenPreviewData(
            state = ProfileCreateState(
                currentStep = ProfileCreateStep.NEED_TERMS
            )
        ),
        ProfileCreateScreenPreviewData(
            state = ProfileCreateState(
                isServiceTermChecked = true,
                currentStep = ProfileCreateStep.NEED_TERMS
            )
        ),
        ProfileCreateScreenPreviewData(
            state = ProfileCreateState(
                isPersonalInfoTermChecked = true,
                currentStep = ProfileCreateStep.NEED_TERMS
            )
        ),
        ProfileCreateScreenPreviewData(
            state = ProfileCreateState(
                isServiceTermChecked = true,
                isPersonalInfoTermChecked = true,
                currentStep = ProfileCreateStep.NEED_TERMS
            )
        ),
    )

}