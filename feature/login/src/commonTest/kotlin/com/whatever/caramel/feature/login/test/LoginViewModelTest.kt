package com.whatever.caramel.feature.login.test

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.exception.code.AuthErrorCode
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.usecase.auth.SignInWithSocialPlatformUseCase
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.testing.domain.AuthTestFactory
import com.whatever.caramel.feature.login.LoginViewModel
import com.whatever.caramel.feature.login.mvi.LoginIntent
import com.whatever.caramel.feature.login.mvi.LoginSideEffect
import com.whatever.caramel.feature.login.social.SocialAuthResult
import com.whatever.caramel.feature.login.social.kakao.KakaoUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private val fakeAuthRepository = FakeAuthRepository()
    private val fakeUserRepository = FakeUserRepository()
    private val singInWithSocialPlatformUseCase =
        SignInWithSocialPlatformUseCase(fakeAuthRepository, fakeUserRepository)
    private var savedStateHandle = SavedStateHandle()
    private val loginViewModel = LoginViewModel(savedStateHandle, singInWithSocialPlatformUseCase)

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        with(fakeAuthRepository) {
            refreshAuthTokenResponse = null
            getAuthTokenResponse = null
            loginWithSocialPlatformResponse = null
            isInvalidIdToken = false
        }
        fakeUserRepository.userStatus = UserStatus.NONE

        savedStateHandle = SavedStateHandle()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `로그인을 성공한 경우 유저 상태에 따라 화면전환을 한다`() = runTest {
        val sideEffectScenario = listOf(
            LoginSideEffect.NavigateToCreateProfile,
            LoginSideEffect.NavigateToConnectCouple,
            LoginSideEffect.NavigateToMain
        )
        AuthTestFactory.createSocialLoginScenario().forEachIndexed { index, scenario ->
            fakeAuthRepository.loginWithSocialPlatformResponse = scenario
            loginViewModel.sideEffect.test {
                loginViewModel.intent(
                    LoginIntent.ClickKakaoLoginButton(
                        SocialAuthResult.Success(KakaoUser(idToken = "valid_token"))
                    )
                )

                val awaitSideEffect = awaitItem()
                assertEquals(
                    expected = sideEffectScenario[index],
                    actual = awaitSideEffect,
                    message = "expected : ${sideEffectScenario[index]} but was $awaitSideEffect"
                )
            }
        }
    }

    @Test
    fun `사용자가 로그인을 취소한 경우 SnackBar로 오류를 표시한다`() = runTest {
        loginViewModel.sideEffect.test {
            loginViewModel.intent(
                LoginIntent.ClickKakaoLoginButton(
                    SocialAuthResult.UserCancelled
                )
            )
            val awaitSideEffect = awaitItem()
            assertEquals(
                expected = LoginSideEffect.ShowErrorSnackBar(AuthErrorCode.LOGIN_CANCELLED),
                actual = awaitSideEffect,
                message = "expected : ${LoginSideEffect.ShowErrorSnackBar(AuthErrorCode.LOGIN_CANCELLED)}, but actual was $awaitSideEffect"
            )
        }
    }

    @Test
    fun `사용자가 로그인 도중 오류가 발생했다면 SnackBar로 오류를 표시한다`() = runTest {
        loginViewModel.sideEffect.test {
            loginViewModel.intent(
                LoginIntent.ClickKakaoLoginButton(
                    SocialAuthResult.Error
                )
            )
            val awaitSideEffect = awaitItem()
            assertEquals(
                expected = LoginSideEffect.ShowErrorSnackBar(AuthErrorCode.LOGIN_FAILED),
                actual = awaitSideEffect,
                message = "expected : ${LoginSideEffect.ShowErrorSnackBar(AuthErrorCode.LOGIN_FAILED)}, but actual was $awaitSideEffect"
            )
        }

        @Test
        fun `로그인 서버 통신 중 오류가 발생한 경우 서버에서 내려준 메세지를 기반으로 SnackBar에 표시한다`() = runTest {
            fakeAuthRepository.isInvalidIdToken = true
            loginViewModel.sideEffect.test {
                loginViewModel.intent(
                    LoginIntent.ClickKakaoLoginButton(
                        SocialAuthResult.Success(KakaoUser(idToken = "invalid_token"))
                    )
                )
                val awaitSideEffect = awaitItem()
                assertEquals(
                    expected = LoginSideEffect.ShowErrorSnackBar("invalid id token"),
                    actual = awaitSideEffect,
                    message = "expected : ${LoginSideEffect.ShowErrorSnackBar("invalid id token")}, but actual was $awaitSideEffect"
                )
            }
        }
    }

    class FakeUserRepository : UserRepository {
        var userStatus = UserStatus.NONE

        override suspend fun getUserStatus(): UserStatus {
            return userStatus
        }

        override suspend fun setUserStatus(status: UserStatus) {
            userStatus = status
        }

        override suspend fun createUserProfile(
            nickname: String,
            birthDay: String,
            agreementServiceTerms: Boolean,
            agreementPrivacyPolicy: Boolean
        ): User {
            return User()
        }
    }

    class FakeAuthRepository : AuthRepository {
        var isInvalidIdToken = false
        var loginWithSocialPlatformResponse: UserAuth? = null
        var refreshAuthTokenResponse: AuthToken? = null
        var getAuthTokenResponse: AuthToken? = null

        override suspend fun loginWithSocialPlatform(
            idToken: String,
            socialLoginType: SocialLoginType
        ): UserAuth {
            if (isInvalidIdToken) {
                throw CaramelException(
                    code = NetworkErrorCode.INVALID_ARGUMENT,
                    message = "invalid id token"
                )
            }
            return loginWithSocialPlatformResponse
                ?: throw CaramelException(code = AppErrorCode.NULL_VALUE, "")
        }

        override suspend fun refreshAuthToken(oldToken: AuthToken): AuthToken {
            return refreshAuthTokenResponse ?: throw CaramelException(
                code = AppErrorCode.NULL_VALUE,
                ""
            )
        }

        override suspend fun saveTokens(authToken: AuthToken) {
            getAuthTokenResponse = authToken
        }

        override suspend fun getAuthToken(): AuthToken {
            return getAuthTokenResponse
                ?: throw CaramelException(code = AppErrorCode.NULL_VALUE, "")
        }
    }
}