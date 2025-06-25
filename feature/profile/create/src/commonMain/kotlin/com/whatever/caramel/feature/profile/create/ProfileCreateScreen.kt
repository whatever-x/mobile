package com.whatever.caramel.feature.profile.create

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.profile.create.components.ProfileCreateBottomBar
import com.whatever.caramel.feature.profile.create.components.ProfileCreateTopBar
import com.whatever.caramel.feature.profile.create.components.Stepper
import com.whatever.caramel.feature.profile.create.components.step.BirthdayStep
import com.whatever.caramel.feature.profile.create.components.step.GenderStep
import com.whatever.caramel.feature.profile.create.components.step.NeedTermsStep
import com.whatever.caramel.feature.profile.create.components.step.NicknameStep
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateIntent
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateState
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateStep

@Composable
internal fun ProfileCreateScreen(
    state: ProfileCreateState,
    onIntent: (ProfileCreateIntent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            },
        containerColor = CaramelTheme.color.background.primary,
        bottomBar = {
            ProfileCreateBottomBar(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding(),
                buttonEnabled = state.isNextButtonEnabled,
                buttonText = state.buttonText,
                onClickButton = { onIntent(ProfileCreateIntent.ClickNextButton) }
            )
        },
        topBar = {
            ProfileCreateTopBar(
                modifier = Modifier.statusBarsPadding(),
                onClickBackButton = { onIntent(ProfileCreateIntent.ClickBackButton) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Stepper(currentIndex = state.currentIndex)

            AnimatedContent(
                transitionSpec = {
                    if (targetState > initialState) {
                        (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                            slideOutHorizontally { width -> -width } + fadeOut())
                    } else {
                        (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                            slideOutHorizontally { width -> width } + fadeOut())
                    }.using(
                        SizeTransform(clip = false)
                    )
                },
                targetState = state.currentStep,
            ) { currentStep ->
                when (currentStep) {
                    ProfileCreateStep.NICKNAME -> {
                        NicknameStep(
                            nickname = state.nickname,
                            onNicknameChange = { nickname ->
                                onIntent(
                                    ProfileCreateIntent.ChangeNickname(
                                        nickname = nickname
                                    )
                                )
                            }
                        )
                    }

                    ProfileCreateStep.GENDER -> {
                        GenderStep(
                            selectedGender = state.gender,
                            onClickGenderButton = { selectedGender ->
                                onIntent(
                                    ProfileCreateIntent.ClickGenderButton(
                                        gender = selectedGender
                                    )
                                )
                            },
                        )
                    }

                    ProfileCreateStep.BIRTHDAY -> {
                        BirthdayStep(
                            dateUiState = state.birthday,
                            onDayChanged = { day -> onIntent(ProfileCreateIntent.ChangeDayPicker(day)) },
                            onYearChanged = { year -> onIntent(ProfileCreateIntent.ChangeYearPicker(year)) },
                            onMonthChanged = { month -> onIntent(ProfileCreateIntent.ChangeMonthPicker(month)) },
                        )
                    }

                    ProfileCreateStep.NEED_TERMS -> {
                        NeedTermsStep(
                            isPersonalInfoTermChecked = state.isPersonalInfoTermChecked,
                            isServiceTermChecked = state.isServiceTermChecked,
                            onClickServiceTerm = { onIntent(ProfileCreateIntent.ToggleServiceTerm) },
                            onClickPersonalInfoTerm = { onIntent(ProfileCreateIntent.TogglePersonalInfoTerm) },
                            onClickServiceTermLabel = { onIntent(ProfileCreateIntent.ClickServiceTermLabel) },
                            onClickPersonalInfoLabel = { onIntent(ProfileCreateIntent.ClickPersonalInfoTermLabel) }
                        )
                    }
                }
            }
        }
    }
}
