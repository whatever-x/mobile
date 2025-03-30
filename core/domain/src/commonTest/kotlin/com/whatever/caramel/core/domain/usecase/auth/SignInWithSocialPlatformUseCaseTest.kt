package com.whatever.caramel.core.domain.usecase.auth

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AuthErrorCode
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.factory.AuthTestFactory
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.domain.vo.user.UserStatus
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class SignInWithSocialPlatformUseCaseTest {
    private val fakeAuthRepository = FakeAuthRepository()
    private val fakeUserRepository = FakeUserRepository()
    private val useCase: SignInWithSocialPlatformUseCase = SignInWithSocialPlatformUseCase(
        authRepository = fakeAuthRepository,
        userRepository = fakeUserRepository
    )

    @BeforeTest
    fun setup() {
        fakeAuthRepository.shouldException = false
        fakeAuthRepository.errorCode = ""
        fakeAuthRepository.refreshAuthTokenResponse = null
        fakeAuthRepository.loginWithSocialPlatformResponse = null

        fakeUserRepository.shouldException = false
        fakeUserRepository.errorCode = ""
        fakeUserRepository.userStatus = null
    }

    @Test
    fun `로그인 or 회원가입 후 토큰과 유저 상태가 저장되어야 한다`() = runTest {
        with(fakeAuthRepository) {
            loginWithSocialPlatformResponse = AuthTestFactory.createNewUserAuth(false)
            getAuthTokenResponse = fakeAuthRepository.loginWithSocialPlatformResponse?.authToken
        }
        fakeUserRepository.userStatus =
            fakeAuthRepository.loginWithSocialPlatformResponse?.userStatus
        useCase(idToken = "valid-id-token", socialLoginType = SocialLoginType.KAKAO)

        val savedToken = fakeAuthRepository.getAuthToken()
        assertSame(
            expected = fakeAuthRepository.loginWithSocialPlatformResponse?.authToken,
            actual = savedToken
        )
        val savedUserStatus = fakeUserRepository.getUserStatus()
        assertSame(
            expected = fakeAuthRepository.loginWithSocialPlatformResponse?.userStatus,
            actual = savedUserStatus
        )
    }

    @Test
    fun `저장되는 토큰은 빈 값이 될 수 없다`() = runTest {
        with(fakeAuthRepository) {
            loginWithSocialPlatformResponse = AuthTestFactory.createNewUserAuth(true)
            getAuthTokenResponse = fakeAuthRepository.loginWithSocialPlatformResponse?.authToken
        }

        val exception = assertFailsWith<CaramelException> {
            useCase(idToken = "valid-id-token", socialLoginType = SocialLoginType.KAKAO)
        }
        assertEquals(expected = AuthErrorCode.EMPTY_AUTH_TOKEN, actual = exception.code)
    }

    @Test
    fun `저장되는 유저 상태는 서버의 결과값을 따라가야 한다`() = runTest {
        fakeAuthRepository.loginWithSocialPlatformResponse = AuthTestFactory.createCoupleUserAuth()
        fakeUserRepository.userStatus =
            fakeAuthRepository.loginWithSocialPlatformResponse?.userStatus

        useCase(idToken = "valid-id-token", socialLoginType = SocialLoginType.KAKAO)

        assertEquals(
            expected = fakeAuthRepository.loginWithSocialPlatformResponse?.userStatus,
            actual = fakeUserRepository.getUserStatus()
        )
    }

    @Test
    fun `idToken은 빈 값이 되어서는 안된다`() = runTest {
        val exception = assertFailsWith<CaramelException> {
            useCase(idToken = "", socialLoginType = SocialLoginType.KAKAO)
        }
        assertEquals(expected = AuthErrorCode.EMPTY_ID_TOKEN, actual = exception.code)
    }
}

class FakeAuthRepository : AuthRepository {
    var shouldException = false
    var errorCode = ""
    var loginWithSocialPlatformResponse: UserAuth? = null
    var refreshAuthTokenResponse: AuthToken? = null
    var getAuthTokenResponse: AuthToken? = null

    override suspend fun loginWithSocialPlatform(
        idToken: String,
        socialLoginType: SocialLoginType
    ): UserAuth {
        if (shouldException) {
            throw CaramelException(code = errorCode, message = "")
        }
        return loginWithSocialPlatformResponse ?: throw CaramelException(
            code = NetworkErrorCode.UNKNOWN,
            message = ""
        )
    }

    override suspend fun refreshAuthToken(oldToken: AuthToken): AuthToken {
        if (shouldException) {
            throw CaramelException(code = errorCode, message = "")
        }
        return refreshAuthTokenResponse
            ?: throw CaramelException(code = NetworkErrorCode.UNKNOWN, message = "")
    }

    override suspend fun saveTokens(authToken: AuthToken) {}

    override suspend fun getAuthToken(): AuthToken {
        return getAuthTokenResponse
            ?: throw CaramelException(code = NetworkErrorCode.UNKNOWN, message = "")
    }
}

class FakeUserRepository : UserRepository {
    var shouldException = false
    var errorCode = ""
    var userStatus: UserStatus? = null

    override suspend fun getUserStatus(): UserStatus {
        if (shouldException) {
            throw CaramelException(code = NetworkErrorCode.UNKNOWN, message = "")
        }
        return userStatus ?: throw CaramelException(code = errorCode, message = "")
    }

    override suspend fun setUserStatus(status: UserStatus) {
        userStatus = status
    }

    override suspend fun createUserProfile(
        nickname: String,
        birthDay: String,
        agreementServiceTerms: Boolean,
        agreementPrivacyPolicy: Boolean
    ) {
    }
}