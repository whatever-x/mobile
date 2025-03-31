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
import com.whatever.caramel.core.testing.util.assertEqualsWithMessage
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

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeAuthRepository: FakeAuthRepository
    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var signInWithSocialPlatformUseCase: SignInWithSocialPlatformUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var loginViewModel: LoginViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeUserRepository = FakeUserRepository()
        fakeAuthRepository = FakeAuthRepository()
        savedStateHandle = SavedStateHandle()

        signInWithSocialPlatformUseCase =
            SignInWithSocialPlatformUseCase(fakeAuthRepository, fakeUserRepository)
        loginViewModel = LoginViewModel(savedStateHandle, signInWithSocialPlatformUseCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    private suspend fun verifyLoginIntent(
        socialAuthResult: SocialAuthResult<KakaoUser>,
        expectedSideEffect: LoginSideEffect
    ) {
        loginViewModel.sideEffect.test {
            loginViewModel.intent(LoginIntent.ClickKakaoLoginButton(socialAuthResult))
            assertEqualsWithMessage(
                expected = expectedSideEffect,
                actual = awaitItem()
            )
        }
    }

    @Test
    fun `회원가입을 한 사용자는 프로필 생성 화면으로 이동한다`() = runTest {
        fakeAuthRepository.loginWithSocialPlatformResponse = AuthTestFactory.createNewUserAuth()
        verifyLoginIntent(
            socialAuthResult = SocialAuthResult.Success(KakaoUser(idToken = VALID_ID_TOKEN)),
            expectedSideEffect = LoginSideEffect.NavigateToCreateProfile
        )
    }

    @Test
    fun `커플이 연결되지 않은 사용자는 로그인을 하면 커플 연결 화면으로 이동한다`() = runTest {
        fakeAuthRepository.loginWithSocialPlatformResponse = AuthTestFactory.createSingleUserAuth()
        verifyLoginIntent(
            socialAuthResult = SocialAuthResult.Success(KakaoUser(idToken = VALID_ID_TOKEN)),
            expectedSideEffect = LoginSideEffect.NavigateToConnectCouple
        )
    }

    @Test
    fun `커플 연결된 사용자는 로그인을 하면 메인 화면으로 이동한다`() = runTest {
        fakeAuthRepository.loginWithSocialPlatformResponse = AuthTestFactory.createCoupleUserAuth()
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
        fakeAuthRepository.isInvalidIdToken = true
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
    private var storedAuthToken: AuthToken? = null

    override suspend fun loginWithSocialPlatform(
        idToken: String,
        socialLoginType: SocialLoginType
    ): UserAuth {
        if (isInvalidIdToken) {
            throw CaramelException(
                code = NetworkErrorCode.INVALID_ARGUMENT,
                message = LoginViewModelTest.NETWORK_INVALID_TOKEN_ERROR_MSG
            )
        }
        return loginWithSocialPlatformResponse
            ?: throw CaramelException(
                code = AppErrorCode.NULL_VALUE,
                "테스트에서 loginWithSocialPlatformResponse 설정 필요"
            )
    }

    override suspend fun refreshAuthToken(oldToken: AuthToken): AuthToken {
        throw UnsupportedOperationException("테스트에서 사용되지 않음")
    }

    override suspend fun saveTokens(authToken: AuthToken) {
        storedAuthToken = authToken
    }

    override suspend fun getAuthToken(): AuthToken {
        return storedAuthToken
            ?: throw CaramelException(code = AppErrorCode.NULL_VALUE, "")
    }
}