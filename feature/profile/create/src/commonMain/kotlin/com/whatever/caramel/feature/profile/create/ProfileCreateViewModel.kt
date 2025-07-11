package com.whatever.caramel.feature.profile.create

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.usecase.user.CreateUserProfileUseCase
import com.whatever.caramel.core.domain.usecase.user.UpdateUserSettingUseCase
import com.whatever.caramel.core.domain.validator.UserValidator
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.util.DateFormatter.createDateString
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateIntent
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateSideEffect
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateState
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateStep
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION

class ProfileCreateViewModel(
    private val createUserProfileUseCase: CreateUserProfileUseCase,
    private val updateUserSettingUseCase: UpdateUserSettingUseCase,
    private val permissionsController: PermissionsController,
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<ProfileCreateState, ProfileCreateSideEffect, ProfileCreateIntent>(savedStateHandle, crashlytics) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): ProfileCreateState = ProfileCreateState()

    override suspend fun handleIntent(intent: ProfileCreateIntent) {
        when (intent) {
            is ProfileCreateIntent.ClickBackButton -> backStep()
            is ProfileCreateIntent.ClickNextButton -> nextStep()
            is ProfileCreateIntent.ClickSystemNavigationBackButton -> backStep()
            is ProfileCreateIntent.ChangeNickname -> inputNickname(nickname = intent.nickname)
            is ProfileCreateIntent.ClickGenderButton -> selectGender(gender = intent.gender)
            is ProfileCreateIntent.TogglePersonalInfoTerm -> togglePersonalTermCheckBox()
            is ProfileCreateIntent.ToggleServiceTerm -> toggleServiceTermCheckBox()
            is ProfileCreateIntent.ClickPersonalInfoTermLabel ->
                postSideEffect(
                    ProfileCreateSideEffect.NavigateToPersonalInfoTermNotion,
                )

            is ProfileCreateIntent.ClickServiceTermLabel -> postSideEffect(ProfileCreateSideEffect.NavigateToServiceTermNotion)
            is ProfileCreateIntent.ChangeDayPicker -> changeDay(day = intent.day)
            is ProfileCreateIntent.ChangeMonthPicker -> changeMonth(month = intent.month)
            is ProfileCreateIntent.ChangeYearPicker -> changeYear(year = intent.year)
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        if (throwable is CaramelException) {
            when (throwable.errorUiType) {
                ErrorUiType.TOAST ->
                    postSideEffect(
                        ProfileCreateSideEffect.ShowErrorToast(
                            message = throwable.message,
                        ),
                    )
                ErrorUiType.DIALOG ->
                    postSideEffect(
                        ProfileCreateSideEffect.ShowErrorDialog(
                            message = throwable.message,
                            description = throwable.description,
                        ),
                    )
            }
        } else {
            caramelCrashlytics.recordException(throwable)
            postSideEffect(
                ProfileCreateSideEffect.ShowErrorDialog(
                    message = "알 수 없는 오류가 발생했습니다.",
                    description = null,
                ),
            )
        }
    }

    private fun backStep() {
        if (currentState.currentStep != ProfileCreateStep.NICKNAME) {
            reduce {
                copy(
                    currentStep = ProfileCreateStep.entries[currentIndex - 1],
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
                    currentStep = ProfileCreateStep.entries[currentIndex + 1],
                )
            }
        } else {
            launch {
                val userStatus =
                    createUserProfileUseCase(
                        nickname = currentState.nickname,
                        birthDay =
                            createDateString(
                                currentState.birthday.year,
                                currentState.birthday.month,
                                currentState.birthday.day,
                            ),
                        gender = currentState.gender,
                        agreementServiceTerms = currentState.isServiceTermChecked,
                        agreementPrivacyPolicy = currentState.isPersonalInfoTermChecked,
                    )

                updateUserSettingUseCase(
                    notificationEnabled =
                        permissionsController.isPermissionGranted(
                            permission = Permission.REMOTE_NOTIFICATION,
                        ),
                )
                postSideEffect(ProfileCreateSideEffect.NavigateToStartDestination(userStatus = userStatus))
            }
        }
    }

    private fun inputNickname(nickname: String) {
        UserValidator.checkInputNicknameValidate(nickname).getOrThrow()
        reduce {
            copy(
                nickname = nickname,
            )
        }
    }

    private fun selectGender(gender: Gender) {
        reduce {
            copy(
                gender = gender,
            )
        }
    }

    private fun toggleServiceTermCheckBox() {
        reduce {
            copy(
                isServiceTermChecked = !currentState.isServiceTermChecked,
            )
        }
    }

    private fun togglePersonalTermCheckBox() {
        reduce {
            copy(
                isPersonalInfoTermChecked = !currentState.isPersonalInfoTermChecked,
            )
        }
    }

    private fun changeYear(year: Int) {
        reduce {
            copy(
                birthday =
                    birthday.copy(
                        year = year,
                    ),
            )
        }

        postSideEffect(ProfileCreateSideEffect.PerformHapticFeedback)
    }

    private fun changeMonth(month: Int) {
        reduce {
            copy(
                birthday =
                    birthday.copy(
                        month = month,
                    ),
            )
        }

        postSideEffect(ProfileCreateSideEffect.PerformHapticFeedback)
    }

    private fun changeDay(day: Int) {
        reduce {
            copy(
                birthday =
                    birthday.copy(
                        day = day,
                    ),
            )
        }

        postSideEffect(ProfileCreateSideEffect.PerformHapticFeedback)
    }
}
