package com.whatever.caramel.feature.profile.create.test

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.usecase.user.CreateUserProfileUseCase
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.testing.repository.TestAuthRepository
import com.whatever.caramel.core.testing.repository.TestUserRepository
import com.whatever.caramel.feature.profile.create.ProfileCreateViewModel
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateIntent
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateSideEffect
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateStep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileCreateViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testUserRepository: TestUserRepository
    private lateinit var testAuthRepository: TestAuthRepository
    private lateinit var viewModel: ProfileCreateViewModel
    private lateinit var createUserProfileUseCase: CreateUserProfileUseCase
    private lateinit var savedStateHandle: SavedStateHandle

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle()
        testAuthRepository = TestAuthRepository()
        testUserRepository = TestUserRepository()
        createUserProfileUseCase = CreateUserProfileUseCase(testUserRepository)
        viewModel = ProfileCreateViewModel(createUserProfileUseCase, savedStateHandle)
    }

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    private fun progressToStep(targetStep: ProfileCreateStep) {
        val currentStep = viewModel.state.value.currentStep
        val steps = ProfileCreateStep.entries
        val currentIndex = steps.indexOf(currentStep)
        val targetIndex = steps.indexOf(targetStep)

        require(targetIndex >= currentIndex) { "뒤로가기는 제공하지 않음" }

        if (currentStep == ProfileCreateStep.NICKNAME && viewModel.state.value.nickname.isEmpty()) {
            viewModel.intent(ProfileCreateIntent.ChangeNickname(VALID_NICKNAME))
        }

        for (i in currentIndex until targetIndex) {
            when (steps[i]) {
                ProfileCreateStep.GENDER -> {
                    if (viewModel.state.value.gender == Gender.IDLE) {
                        viewModel.intent(ProfileCreateIntent.ClickGenderButton(Gender.MALE))
                    }
                }

                ProfileCreateStep.NEED_TERMS -> {
                    if (!viewModel.state.value.isPersonalInfoTermChecked) {
                        viewModel.intent(ProfileCreateIntent.TogglePersonalInfoTerm)
                    }
                    if (!viewModel.state.value.isServiceTermChecked) {
                        viewModel.intent(ProfileCreateIntent.ToggleServiceTerm)
                    }
                }

                else -> {}
            }
            viewModel.intent(ProfileCreateIntent.ClickNextButton)
            testDispatcher.scheduler.advanceUntilIdle()
        }
    }

    @Test
    fun `프로필 생성에 성공하면 커플 연결 페이지로 이동합니다`() = runTest {
        testUserRepository.createdUser = User()
        viewModel.sideEffect.test {
            progressToStep(ProfileCreateStep.NEED_TERMS)
            viewModel.intent(ProfileCreateIntent.ToggleServiceTerm)
            viewModel.intent(ProfileCreateIntent.TogglePersonalInfoTerm)
            viewModel.intent(ProfileCreateIntent.ClickNextButton)
            assertEquals(
                expected = ProfileCreateSideEffect.NavigateToConnectCouple,
                actual = awaitItem()
            )
        }
    }

    @Test
    fun `Back Button 클릭 시 모든 프로필 생성 단계가 뒤로가져야 합니다`() = runTest {
        // 마지막 step(이용약관)으로 이동
        repeat(ProfileCreateStep.entries.size - 1) {
            viewModel.intent(ProfileCreateIntent.ClickNextButton)
        }

        for (step in ProfileCreateStep.entries.size - 1 downTo 0) {
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(
                expected = ProfileCreateStep.entries[step],
                actual = viewModel.state.value.currentStep
            )
            viewModel.intent(ProfileCreateIntent.ClickBackButton)
        }

        viewModel.sideEffect.test {
            assertEquals(
                expected = ProfileCreateSideEffect.NavigateToLogin,
                actual = awaitItem()
            )
        }
    }

    @Test
    fun `Next Button 클릭 시 모든 프로필 단계가 순차적으로 진행되야 합니다`() = runTest {
        ProfileCreateStep.entries.forEach { step ->
            progressToStep(step)

            assertEquals(
                expected = viewModel.state.value.currentStep,
                actual = step
            )
        }
    }

    @Test
    fun `닉네임 입력 시 8글자이하 만 입력되야 합니다`() = runTest {
        viewModel.intent(ProfileCreateIntent.ChangeNickname(VALID_NICKNAME))
        viewModel.intent(ProfileCreateIntent.ChangeNickname(INVALID_SIZE_NICKNAME))
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            expected = VALID_NICKNAME,
            actual = viewModel.state.value.nickname
        )
    }

    @Test
    fun `닉네임 입력 시 특수문자와 공백은 입력되지 않습니다`() = runTest {
        viewModel.intent(ProfileCreateIntent.ChangeNickname(INVALID_NICKNAME_CHARACTER))
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            expected = "",
            actual = viewModel.state.value.nickname
        )
    }

    @Test
    fun `생일 화면은 항상 다음 버튼이 활성화되어 있습니다`() = runTest {
        repeat(2) {
            viewModel.intent(ProfileCreateIntent.ClickNextButton)
        }
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            expected = viewModel.state.value.currentStep,
            actual = ProfileCreateStep.BIRTHDAY
        )

        assertEquals(
            expected = true,
            actual = viewModel.state.value.isNextButtonEnabled
        )
    }

    @Test
    fun `성별이 선택 되어야 다음 버튼이 활성화 됩니다`() = runTest {
        viewModel.intent(ProfileCreateIntent.ClickNextButton)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            expected = false,
            actual = viewModel.state.value.isNextButtonEnabled
        )
        assertEquals(
            expected = ProfileCreateStep.GENDER,
            actual = viewModel.state.value.currentStep
        )
        viewModel.intent(ProfileCreateIntent.ClickGenderButton(Gender.MALE))
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            expected = true,
            actual = viewModel.state.value.isNextButtonEnabled
        )
    }

    @Test
    fun `닉네임이 존재할 경우에만 다음 버튼이 활성화 됩니다`() = runTest {
        assertEquals(
            expected = false,
            actual = viewModel.state.value.isNextButtonEnabled
        )
        viewModel.intent(ProfileCreateIntent.ChangeNickname(VALID_NICKNAME))
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            expected = true,
            actual = viewModel.state.value.isNextButtonEnabled
        )
    }

    @Test
    fun `약관 동의가 전부 되어야 가입 버튼이 활성화 됩니다`() = runTest {
        repeat(3) {
            viewModel.intent(ProfileCreateIntent.ClickNextButton)
        }
        viewModel.intent(ProfileCreateIntent.TogglePersonalInfoTerm)
        viewModel.intent(ProfileCreateIntent.ToggleServiceTerm)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            expected = ProfileCreateStep.NEED_TERMS,
            actual = viewModel.state.value.currentStep
        )
        assertEquals(
            expected = true,
            actual = viewModel.state.value.isNextButtonEnabled
        )
        viewModel.intent(ProfileCreateIntent.ToggleServiceTerm)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            expected = false,
            actual = viewModel.state.value.isNextButtonEnabled
        )
    }

    companion object {
        private const val VALID_NICKNAME = "12345678"
        private const val INVALID_SIZE_NICKNAME = "123456789"
        private const val INVALID_NICKNAME_CHARACTER = "   !@#$"
    }
}