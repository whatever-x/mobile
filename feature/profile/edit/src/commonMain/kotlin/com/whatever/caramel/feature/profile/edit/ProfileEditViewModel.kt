package com.whatever.caramel.feature.profile.edit

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.domain.usecase.couple.EditCoupleStartDateUseCase
import com.whatever.caramel.core.domain.usecase.user.EditProfileUseCase
import com.whatever.caramel.core.domain.validator.UserValidator
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.util.DateFormatter
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditIntent
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditSideEffect
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditState
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditType
import com.whatever.caramel.feature.profile.edit.navigation.ProfileEditRoute
import io.github.aakira.napier.Napier

class ProfileEditViewModel(
    private val editProfileUseCase: EditProfileUseCase,
    private val editCoupleStartDateUseCase: EditCoupleStartDateUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProfileEditState, ProfileEditSideEffect, ProfileEditIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): ProfileEditState {
        val arguments = savedStateHandle.toRoute<ProfileEditRoute>()
        return ProfileEditState(
            editUiType = ProfileEditType.valueOf(arguments.editType),
            nickName = arguments.nickname,
            birthDay = if (arguments.birthdayMillisecond == 0L) {
                DateUiState.currentDate()
            } else {
                DateUiState.get(arguments.birthdayMillisecond)
            },
            startDate = if (arguments.startDateMillisecond == 0L) {
                DateUiState.currentDate()
            } else {
                DateUiState.get(arguments.startDateMillisecond)
            }
        )
    }

    override suspend fun handleIntent(intent: ProfileEditIntent) {
        when (intent) {
            is ProfileEditIntent.ClickSaveButton -> clickSaveButton()
            is ProfileEditIntent.ClickCloseButton -> postSideEffect(ProfileEditSideEffect.PopBackStack)
            is ProfileEditIntent.ChangeNickname -> updateNickname(intent.nickname)
            is ProfileEditIntent.ChangeBirthDayDayPicker -> updateBirthdayDay(intent.day)
            is ProfileEditIntent.ChangeBirthDayMonthPicker -> updateBirthdayMonth(intent.month)
            is ProfileEditIntent.ChangeBirthDayYearPicker -> updateBirthdayYear(intent.year)
            is ProfileEditIntent.ChangeDDayDayPicker -> updateStartDateDay(intent.day)
            is ProfileEditIntent.ChangeDDayMonthPicker -> updateStartDateMonth(intent.month)
            is ProfileEditIntent.ChangeDDayYearPicker -> updateStartDateYear(intent.year)
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        Napier.e { "throwable: $throwable" }
    }

    private fun updateNickname(nickname: String) {
        UserValidator.checkInputNicknameValidate(nickname)
            .onSuccess {
                reduce {
                    copy(
                        nickName = nickname,
                    )
                }
            }
    }

    private fun updateBirthdayYear(year: Int) {
        reduce {
            copy(
                birthDay = birthDay.copy(year = year)
            )
        }
    }

    private fun updateBirthdayMonth(month: Int) {
        reduce {
            copy(
                birthDay = birthDay.copy(month = month)
            )
        }
    }

    private fun updateBirthdayDay(day: Int) {
        reduce {
            copy(
                birthDay = birthDay.copy(day = day)
            )
        }
    }

    private fun updateStartDateYear(year: Int) {
        reduce {
            copy(
                startDate = startDate.copy(year = year)
            )
        }
    }

    private fun updateStartDateMonth(month: Int) {
        reduce {
            copy(
                startDate = startDate.copy(month = month)
            )
        }
    }

    private fun updateStartDateDay(day: Int) {
        reduce {
            copy(
                startDate = startDate.copy(day = day)
            )
        }
    }

    private suspend fun clickSaveButton() {
        when (currentState.editUiType) {
            ProfileEditType.NONE -> {}
            ProfileEditType.NICKNAME -> editProfileUseCase(
                nickname = currentState.nickName
            )

            ProfileEditType.BIRTHDAY -> editProfileUseCase(
                birthday = DateFormatter.createDateString(
                    year = currentState.birthDay.year,
                    month = currentState.birthDay.month,
                    day = currentState.birthDay.day
                )
            )

            ProfileEditType.START_DATE -> editCoupleStartDateUseCase(
                startDate = DateFormatter.createDateString(
                    year = currentState.startDate.year,
                    month = currentState.startDate.month,
                    day = currentState.startDate.day
                )
            )
        }
        postSideEffect(ProfileEditSideEffect.PopBackStack)
    }
}