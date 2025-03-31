package com.whatever.caramel.feature.splash.test

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AuthErrorCode
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.usecase.user.RefreshUserSessionUseCase
import com.whatever.caramel.core.domain.vo.auth.AuthToken
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.domain.vo.auth.UserAuth
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.testing.domain.AuthTestFactory
import com.whatever.caramel.core.testing.util.TestConstants
import com.whatever.caramel.core.testing.util.assertEqualsWithMessage
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

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeAuthRepository: FakeAuthRepository
    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var refreshUserSessionUseCase: RefreshUserSessionUseCase
    private var splashViewModel: SplashViewModel? = null

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle()

        fakeAuthRepository = FakeAuthRepository()
        fakeUserRepository = FakeUserRepository()
        refreshUserSessionUseCase =
            RefreshUserSessionUseCase(fakeAuthRepository, fakeUserRepository)
        splashViewModel = null
    }

    @AfterTest
    fun tearDown() {
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
            assertEqualsWithMessage(
                expected = expectedSplashSideEffect,
                actual = awaitItem()
            )
        }
    }

    @Test
    fun `토큰 갱신이 실패한 경우 로그인 화면으로 이동한다`() = runTest {
        fakeAuthRepository.isRefreshFail = true
        verifySplashSideEffect(
            expectedSplashSideEffect = SplashSideEffect.NavigateToLogin
        )
    }

    @Test
    fun `토큰 갱신이 성공한 경우 로컬 저장소에 저장된 유저 상태가 커플인 경우 메인 페이지로 이동한다`() = runTest {
        fakeUserRepository.savedUserStatus = UserStatus.COUPLED
        fakeAuthRepository.saveAuthToken = AuthTestFactory.createValidAuthToken()
        fakeAuthRepository.refreshAuthTokenResponse = AuthTestFactory.createValidAuthToken()
        verifySplashSideEffect(
            expectedSplashSideEffect = SplashSideEffect.NavigateToMain
        )
    }

    @Test
    fun `토큰 갱신이 성공한 경우 로컬 저장소에 저장된 유저 상태가 싱글인 경우 커플 초대로 이동한다`() = runTest {
        fakeUserRepository.savedUserStatus = UserStatus.SINGLE
        fakeAuthRepository.saveAuthToken = AuthTestFactory.createValidAuthToken()
        fakeAuthRepository.refreshAuthTokenResponse = AuthTestFactory.createValidAuthToken()

        verifySplashSideEffect(
            expectedSplashSideEffect = SplashSideEffect.NavigateToInviteCouple
        )
    }

    @Test
    fun `토큰 갱신이 성공한 경우 로컬 저장소에 저장된 유저 상태가 회원가입 이후 아무것도 안한 경우 프로필 생성으로 이동한다`() = runTest {
        fakeUserRepository.savedUserStatus = UserStatus.NEW
        fakeAuthRepository.saveAuthToken = AuthTestFactory.createEmptyAuthToken()
        fakeAuthRepository.refreshAuthTokenResponse = AuthTestFactory.createValidAuthToken()

        verifySplashSideEffect(
            expectedSplashSideEffect = SplashSideEffect.NavigateToCreateProfile
        )
    }

    companion object {
        const val REFRESH_TOKEN_ERROR_MSG = "refresh token error"
    }
}

class FakeUserRepository : UserRepository {
    var savedUserStatus: UserStatus? = null

    override suspend fun getUserStatus(): UserStatus {
        return savedUserStatus ?: throw CaramelException(
            code = NetworkErrorCode.UNKNOWN,
            message = TestConstants.REQUIRE_INIT_FOR_TEST
        )
    }

    override suspend fun setUserStatus(status: UserStatus) {
        savedUserStatus = status
    }

    override suspend fun createUserProfile(
        nickname: String,
        birthDay: String,
        agreementServiceTerms: Boolean,
        agreementPrivacyPolicy: Boolean
    ): User {
        throw UnsupportedOperationException(TestConstants.NOT_USE_IN_TEST)
    }
}

class FakeAuthRepository : AuthRepository {
    var saveAuthToken: AuthToken? = null
    var refreshAuthTokenResponse: AuthToken? = null
    var isRefreshFail = false

    override suspend fun loginWithSocialPlatform(
        idToken: String,
        socialLoginType: SocialLoginType
    ): UserAuth {
        throw UnsupportedOperationException(TestConstants.NOT_USE_IN_TEST)
    }

    override suspend fun refreshAuthToken(oldToken: AuthToken): AuthToken {
        if(isRefreshFail){
            throw CaramelException(
                code = AuthErrorCode.UNAUTHORIZED,
                message = SplashViewModelTest.REFRESH_TOKEN_ERROR_MSG
            )
        }
        return refreshAuthTokenResponse
            ?: throw CaramelException(
                code = NetworkErrorCode.UNKNOWN,
                message = TestConstants.REQUIRE_INIT_FOR_TEST
            )
    }

    override suspend fun saveTokens(authToken: AuthToken) {
        saveAuthToken = authToken
    }

    override suspend fun getAuthToken(): AuthToken {
        return saveAuthToken ?: throw CaramelException(
            code = NetworkErrorCode.UNKNOWN,
            message = TestConstants.REQUIRE_INIT_FOR_TEST
        )
    }
}