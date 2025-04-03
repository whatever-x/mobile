package com.whatever.caramel.feature.splash.test

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.whatever.caramel.core.domain.usecase.user.RefreshUserSessionUseCase
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.testing.factory.AuthTestFactory
import com.whatever.caramel.core.testing.repository.TestAuthRepository
import com.whatever.caramel.core.testing.repository.TestUserRepository
import com.whatever.caramel.feature.splash.SplashViewModel
import com.whatever.caramel.feature.splash.mvi.SplashSideEffect
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
class SplashViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testAuthRepository: TestAuthRepository
    private lateinit var testUserRepository: TestUserRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var refreshUserSessionUseCase: RefreshUserSessionUseCase
    private var splashViewModel: SplashViewModel? = null

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle()

        testAuthRepository = TestAuthRepository()
        testUserRepository = TestUserRepository()
        refreshUserSessionUseCase =
            RefreshUserSessionUseCase(testAuthRepository, testUserRepository)
        splashViewModel = null
    }

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    private suspend fun verifySplashSideEffect(
        expectedSplashSideEffect: SplashSideEffect
    ) {
        splashViewModel = SplashViewModel(
            refreshUserSessionUseCase, savedStateHandle
        )
        splashViewModel?.sideEffect?.test {
            assertEquals(
                expected = expectedSplashSideEffect,
                actual = awaitItem()
            )
        }
    }

    @Test
    fun `토큰 갱신이 실패한 경우 로그인 화면으로 이동한다`() = runTest {
        testAuthRepository.isRefreshFail = true
        verifySplashSideEffect(
            expectedSplashSideEffect = SplashSideEffect.NavigateToLogin
        )
    }

    @Test
    fun `토큰 갱신이 성공한 경우 로컬 저장소에 저장된 유저 상태가 커플인 경우 메인 페이지로 이동한다`() = runTest {
        testUserRepository.savedUserStatus = UserStatus.COUPLED
        testAuthRepository.saveAuthToken = AuthTestFactory.createValidAuthToken()
        testAuthRepository.refreshAuthTokenResponse = AuthTestFactory.createValidAuthToken()
        verifySplashSideEffect(
            expectedSplashSideEffect = SplashSideEffect.NavigateToMain
        )
    }

    @Test
    fun `토큰 갱신이 성공한 경우 로컬 저장소에 저장된 유저 상태가 싱글인 경우 커플 초대로 이동한다`() = runTest {
        testUserRepository.savedUserStatus = UserStatus.SINGLE
        testAuthRepository.saveAuthToken = AuthTestFactory.createValidAuthToken()
        testAuthRepository.refreshAuthTokenResponse = AuthTestFactory.createValidAuthToken()

        verifySplashSideEffect(
            expectedSplashSideEffect = SplashSideEffect.NavigateToInviteCouple
        )
    }

    @Test
    fun `토큰 갱신이 성공한 경우 로컬 저장소에 저장된 유저 상태가 회원가입 이후 아무것도 안한 경우 프로필 생성으로 이동한다`() = runTest {
        testUserRepository.savedUserStatus = UserStatus.NEW
        testAuthRepository.saveAuthToken = AuthTestFactory.createEmptyAuthToken()
        testAuthRepository.refreshAuthTokenResponse = AuthTestFactory.createValidAuthToken()

        verifySplashSideEffect(
            expectedSplashSideEffect = SplashSideEffect.NavigateToCreateProfile
        )
    }
}