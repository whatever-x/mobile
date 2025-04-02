package com.whatever.caramel.feature.login.test

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.whatever.caramel.core.domain.exception.code.AuthErrorCode
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.usecase.auth.SignInWithSocialPlatformUseCase
import com.whatever.caramel.core.testing.factory.AuthTestFactory
import com.whatever.caramel.core.testing.repository.TestAuthRepository
import com.whatever.caramel.core.testing.repository.TestUserRepository
import com.whatever.caramel.feature.login.LoginViewModel
import com.whatever.caramel.feature.login.mvi.LoginIntent
import com.whatever.caramel.feature.login.mvi.LoginSideEffect
import com.whatever.caramel.feature.login.social.SocialAuthResult
import com.whatever.caramel.feature.login.social.kakao.KakaoUser
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
class LoginViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testAuthRepository: TestAuthRepository
    private lateinit var testUserRepository: TestUserRepository
    private lateinit var signInWithSocialPlatformUseCase: SignInWithSocialPlatformUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var loginViewModel: LoginViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        testUserRepository = TestUserRepository()
        testAuthRepository = TestAuthRepository()
        savedStateHandle = SavedStateHandle()

        signInWithSocialPlatformUseCase =
            SignInWithSocialPlatformUseCase(testAuthRepository, testUserRepository)
        loginViewModel = LoginViewModel(savedStateHandle, signInWithSocialPlatformUseCase)
    }

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
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

    @Test
    fun `회원가입을 한 사용자는 프로필 생성 화면으로 이동한다`() = runTest {
        testAuthRepository.loginWithSocialPlatformResponse = AuthTestFactory.createNewUserAuth()
        verifyLoginIntent(
            socialAuthResult = SocialAuthResult.Success(KakaoUser(idToken = VALID_ID_TOKEN)),
            expectedSideEffect = LoginSideEffect.NavigateToCreateProfile
        )
    }

    @Test
    fun `커플이 연결되지 않은 사용자는 로그인을 하면 커플 연결 화면으로 이동한다`() = runTest {
        testAuthRepository.loginWithSocialPlatformResponse = AuthTestFactory.createSingleUserAuth()
        verifyLoginIntent(
            socialAuthResult = SocialAuthResult.Success(KakaoUser(idToken = VALID_ID_TOKEN)),
            expectedSideEffect = LoginSideEffect.NavigateToConnectCouple
        )
    }

    @Test
    fun `커플 연결된 사용자는 로그인을 하면 메인 화면으로 이동한다`() = runTest {
        testAuthRepository.loginWithSocialPlatformResponse = AuthTestFactory.createCoupleUserAuth()
        verifyLoginIntent(
            socialAuthResult = SocialAuthResult.Success(KakaoUser(idToken = VALID_ID_TOKEN)),
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
        testAuthRepository.isInvalidIdToken = true
        verifyLoginIntent(
            socialAuthResult = SocialAuthResult.Success(KakaoUser(idToken = INVALID_ID_TOKEN)),
            expectedSideEffect = LoginSideEffect.ShowErrorSnackBar(
                code = NetworkErrorCode.INVALID_ARGUMENT,
                message = NETWORK_INVALID_TOKEN_ERROR_MSG
            )
        )
    }

    companion object {
        const val VALID_ID_TOKEN = "valid_token"
        const val INVALID_ID_TOKEN = "invalid_token"
        const val NETWORK_INVALID_TOKEN_ERROR_MSG = "invalid_token_error_message"
    }
}