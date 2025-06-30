package com.whatever.caramel.feature.splash.test

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.whatever.caramel.core.domain.di.useCaseModule
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AuthErrorCode
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.testing.constants.TestMessage
import com.whatever.caramel.core.testing.factory.AuthTestFactory
import com.whatever.caramel.feature.splash.SplashViewModel
import com.whatever.caramel.feature.splash.di.splashFeatureModule
import com.whatever.caramel.feature.splash.mvi.SplashSideEffect
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest : KoinComponent {
    private val testDispatcher = StandardTestDispatcher()
    private val authRepository = mock<AuthRepository>()
    private val userRepository = mock<UserRepository>()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(
                module {
                    single<AuthRepository> { authRepository }
                    single<UserRepository> { userRepository }
                    single<SavedStateHandle> { SavedStateHandle() }
                },
                splashFeatureModule,
                useCaseModule,
            )
        }
    }

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
        stopKoin()
    }

    private suspend fun verifySplashSideEffect(expectedSplashSideEffect: SplashSideEffect) {
        val viewModel = get<SplashViewModel>()
        viewModel.sideEffect.test {
            assertEquals(
                expected = expectedSplashSideEffect,
                actual = awaitItem(),
            )
        }
    }

    private fun determineUserStatusAfterTokenRefresh(userStatus: UserStatus) {
        everySuspend {
            authRepository.getAuthToken()
        } returns AuthTestFactory.createExpiredToken()

        everySuspend {
            authRepository.refreshAuthToken(oldToken = AuthTestFactory.createExpiredToken())
        } returns AuthTestFactory.createValidToken()

        everySuspend {
            authRepository.saveTokens(AuthTestFactory.createValidToken())
        } returns Unit

        everySuspend {
            userRepository.getUserStatus()
        } returns userStatus
    }

    @Test
    fun `토큰 갱신이 실패한 경우 로그인 화면으로 이동한다`() =
        runTest {
            everySuspend {
                authRepository.refreshAuthToken(any())
            } throws
                CaramelException(
                    code = AuthErrorCode.UNAUTHORIZED,
                    message = TestMessage.FAIL_REFRESH_TOKEN,
                )

            verifySplashSideEffect(
                expectedSplashSideEffect = SplashSideEffect.NavigateToLogin,
            )
        }

    @Test
    fun `토큰 갱신이 성공한 경우 로컬 저장소에 저장된 유저 상태가 커플이라면 메인 페이지로 이동한다`() =
        runTest {
            determineUserStatusAfterTokenRefresh(userStatus = UserStatus.COUPLED)
            verifySplashSideEffect(
                expectedSplashSideEffect = SplashSideEffect.NavigateToMain,
            )
        }

    @Test
    fun `토큰 갱신이 성공한 경우 로컬 저장소에 저장된 유저 상태가 싱글이라면 커플 초대로 이동한다`() =
        runTest {
            determineUserStatusAfterTokenRefresh(userStatus = UserStatus.SINGLE)
            verifySplashSideEffect(
                expectedSplashSideEffect = SplashSideEffect.NavigateToInviteCouple,
            )
        }

    @Test
    fun `토큰 갱신이 성공한 경우 로컬 저장소에 저장된 유저 상태가 회원가입 이후 아무것도 안한 상태라면 프로필 생성으로 이동한다`() =
        runTest {
            determineUserStatusAfterTokenRefresh(userStatus = UserStatus.NEW)
            verifySplashSideEffect(
                expectedSplashSideEffect = SplashSideEffect.NavigateToCreateProfile,
            )
        }
}
