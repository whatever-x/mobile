package com.whatever.caramel.feature.login.test

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.whatever.caramel.core.domain.di.useCaseModule
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AuthErrorCode
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.testing.constants.TestAuthInfo
import com.whatever.caramel.core.testing.constants.TestMessage
import com.whatever.caramel.core.testing.factory.AuthTestFactory
import com.whatever.caramel.feature.login.LoginViewModel
import com.whatever.caramel.feature.login.di.loginFeatureModule
import com.whatever.caramel.feature.login.mvi.LoginIntent
import com.whatever.caramel.feature.login.mvi.LoginSideEffect
import com.whatever.caramel.feature.login.social.SocialAuthResult
import com.whatever.caramel.feature.login.social.kakao.KakaoUser
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
class LoginViewModelTest : KoinComponent {
    private val testDispatcher = StandardTestDispatcher()
    private val authRepository = mock<AuthRepository>()
    private val userRepository = mock<UserRepository>()
    private lateinit var loginViewModel: LoginViewModel

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
                loginFeatureModule,
                useCaseModule
            )
        }
        loginViewModel = get<LoginViewModel>()
    }

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
        stopKoin()
    }

    private suspend fun verifyLoginIntent(
        socialAuthResult: SocialAuthResult<KakaoUser>,
        expectedSideEffect: LoginSideEffect
    ) {
        loginViewModel.sideEffect.test {
            loginViewModel.intent(LoginIntent.ClickKakaoLoginButton(socialAuthResult))
            assertEquals(
                expected = expectedSideEffect,
                actual = awaitItem()
            )
        }
    }

    private fun determineUserStatusAfterLogin(userStatus: UserStatus) {
        val loginResponse = AuthTestFactory.createCoupleUserAuth()
        everySuspend {
            authRepository.loginWithSocialPlatform(idToken = any(), socialLoginType = any())
        } returns loginResponse

        everySuspend {
            authRepository.saveTokens(loginResponse.authToken)
        } returns Unit

        everySuspend {
            userRepository.setUserStatus(userStatus)
        } returns Unit
    }

    @Test
    fun `회원가입을 한 사용자는 프로필 생성 화면으로 이동한다`() = runTest {
        determineUserStatusAfterLogin(userStatus = UserStatus.NEW)
        verifyLoginIntent(
            socialAuthResult = SocialAuthResult.Success(KakaoUser(TestAuthInfo.ID_TOKEN)),
            expectedSideEffect = LoginSideEffect.NavigateToCreateProfile
        )
    }

    @Test
    fun `커플이 연결되지 않은 사용자는 로그인을 하면 커플 연결 화면으로 이동한다`() = runTest {
        determineUserStatusAfterLogin(userStatus = UserStatus.SINGLE)
        verifyLoginIntent(
            socialAuthResult = SocialAuthResult.Success(KakaoUser(TestAuthInfo.ID_TOKEN)),
            expectedSideEffect = LoginSideEffect.NavigateToConnectCouple
        )
    }

    @Test
    fun `커플 연결된 사용자는 로그인을 하면 메인 화면으로 이동한다`() = runTest {
        determineUserStatusAfterLogin(userStatus = UserStatus.COUPLED)
        verifyLoginIntent(
            socialAuthResult = SocialAuthResult.Success(KakaoUser(TestAuthInfo.ID_TOKEN)),
            expectedSideEffect = LoginSideEffect.NavigateToMain
        )
    }

    @Test
    fun `사용자가 로그인을 취소한 경우 SnackBar로 오류를 표시한다`() = runTest {
        verifyLoginIntent(
            socialAuthResult = SocialAuthResult.UserCancelled,
            expectedSideEffect = LoginSideEffect.ShowErrorSnackBar(code = AuthErrorCode.LOGIN_CANCELLED),
        )
    }

    @Test
    fun `사용자가 로그인 도중 오류가 발생했다면 SnackBar로 오류를 표시한다`() = runTest {
        verifyLoginIntent(
            socialAuthResult = SocialAuthResult.Error,
            expectedSideEffect = LoginSideEffect.ShowErrorSnackBar(code = AuthErrorCode.LOGIN_FAILED),
        )
    }

    @Test
    fun `로그인 서버 통신 중 오류가 발생한 경우 서버에서 내려준 메세지를 기반으로 SnackBar에 표시한다`() = runTest {
        everySuspend {
            authRepository.loginWithSocialPlatform(idToken = any(), socialLoginType = any())
        } throws CaramelException(
            code = NetworkErrorCode.UNKNOWN,
            message = TestMessage.FAIL_LOGIN
        )

        verifyLoginIntent(
            socialAuthResult = SocialAuthResult.Success(KakaoUser(TestAuthInfo.ID_TOKEN)),
            expectedSideEffect = LoginSideEffect.ShowErrorSnackBar(
                code = NetworkErrorCode.UNKNOWN,
                message = TestMessage.FAIL_LOGIN
            )
        )
    }
}