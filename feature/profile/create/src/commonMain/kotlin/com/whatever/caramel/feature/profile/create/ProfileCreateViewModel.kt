package com.whatever.caramel.feature.profile.create

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.user.CreateUserProfileUseCase
import com.whatever.caramel.core.domain.validator.UserValidator
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateIntent
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateSideEffect
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateState
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateStep

class ProfileCreateViewModel(
    private val createUserProfileUseCase: CreateUserProfileUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProfileCreateState, ProfileCreateSideEffect, ProfileCreateIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): ProfileCreateState {
        return ProfileCreateState()
    }

    override suspend fun handleIntent(intent: ProfileCreateIntent) {
        when (intent) {
            is ProfileCreateIntent.ClickBackButton -> backStep()
            is ProfileCreateIntent.ClickNextButton -> nextStep()
            is ProfileCreateIntent.ClickSystemNavigationBackButton -> backStep()
            is ProfileCreateIntent.ChangeNickname -> inputNickname(nickname = intent.nickname)
            is ProfileCreateIntent.ClickGenderButton -> selectGender(gender = intent.gender)
            is ProfileCreateIntent.TogglePersonalInfoTerm -> togglePersonalTermCheckBox()
            is ProfileCreateIntent.ToggleServiceTerm -> toggleServiceTermCheckBox()
            is ProfileCreateIntent.ClickPersonalInfoTermLabel -> postSideEffect(ProfileCreateSideEffect.NavigateToPersonalInfoTermNotion)
            is ProfileCreateIntent.ClickServiceTermLabel -> postSideEffect(ProfileCreateSideEffect.NavigateToServiceTermNotion)
        }
    }

    private fun backStep() {
        if (currentState.currentStep != ProfileCreateStep.NICKNAME) {
            reduce {
                copy(
                    currentStep = ProfileCreateStep.entries[currentIndex - 1]
                )
            }
        } else {
            postSideEffect(ProfileCreateSideEffect.NavigateToLogin)
        }
    }

    private suspend fun nextStep() {
        if (currentState.currentStep != ProfileCreateStep.NEED_TERMS) {
            reduce {
                copy(
                    currentStep = ProfileCreateStep.entries[currentIndex + 1]
                )
            }
        } else {
            launch {
                createUserProfileUseCase(
                    nickname = currentState.nickname,
                    birthDay = "${currentState.birthday.year}-${currentState.birthday.month}-${currentState.birthday.day}",
                    gender = currentState.gender,
                    agreementServiceTerms = currentState.isServiceTermChecked,
                    agreementPrivacyPolicy = currentState.isPersonalInfoTermChecked
                )
                postSideEffect(ProfileCreateSideEffect.NavigateToConnectCouple)
            }
        }
    }

    private fun inputNickname(nickname: String) {
        UserValidator.checkInputNicknameValidate(nickname).getOrThrow()
        reduce {
            copy(
                nickname = nickname
            )
        }
    }

    private fun selectGender(gender: Gender) {
        reduce {
            copy(
                gender = gender
            )
        }
    }

    private fun toggleServiceTermCheckBox() {
        reduce {
            copy(
                isServiceTermChecked = !currentState.isServiceTermChecked
            )
        }
    }
    
    private fun togglePersonalTermCheckBox() {
        reduce {
            copy(
                isPersonalInfoTermChecked = !currentState.isPersonalInfoTermChecked
            )
        }
    }
}