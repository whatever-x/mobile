package com.whatever.caramel.feature.profile.create.mvi

import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.viewmodel.UiState

data class ProfileCreateState(
    val nickname: String = "",
    val gender: Gender = Gender.IDLE,
    val birthday: DateUiState = DateUiState.currentDate(),
    val isServiceTermChecked: Boolean = false,
    val isPersonalInfoTermChecked: Boolean = false,
    val currentStep: ProfileCreateStep = ProfileCreateStep.NICKNAME,
) : UiState {
    val isNextButtonEnabled: Boolean
        get() =
            when (currentStep) {
                ProfileCreateStep.NICKNAME -> nickname.isNotEmpty()
                ProfileCreateStep.GENDER -> gender != Gender.IDLE
                ProfileCreateStep.BIRTHDAY -> true
                ProfileCreateStep.NEED_TERMS -> isServiceTermChecked && isPersonalInfoTermChecked
            }

    val currentIndex: Int
        get() = ProfileCreateStep.entries.indexOf(currentStep)

    val buttonText: String
        get() =
            when (currentStep) {
                ProfileCreateStep.NEED_TERMS -> "가입 완료!"
                else -> "다음"
            }
}

enum class ProfileCreateStep {
    NICKNAME,
    GENDER,
    BIRTHDAY,
    NEED_TERMS,
}
